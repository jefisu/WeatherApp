package com.jefisu.weather.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jefisu.weather.core.connectivity.ConnectivityObserver
import com.jefisu.weather.core.util.UiText
import com.jefisu.weather.domain.LocationTracker
import com.jefisu.weather.domain.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
    connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private var _weatherJob: Job? = null
    private var _hasInternet = false

    init {
        connectivityObserver.observe()
            .onEach { status ->
                _hasInternet = status == ConnectivityObserver.Status.Available
                if (!_hasInternet) {
                    _state.update {
                        it.copy(
                            error = UiError.InternetConnection,
                            isLoading = false,
                            weatherInfo = null
                        )
                    }
                }
                getWeatherForecast()
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.SelectDay -> {
                _state.update { it.copy(daySelected = event.day) }
            }
            HomeEvent.LoadDataForecast -> getWeatherForecast()
        }
    }

    private fun getWeatherForecast() {
        if (!_hasInternet) {
            return
        }

        _weatherJob?.cancel()
        _weatherJob = viewModelScope.launch {
            val locationResult = locationTracker.getCurrentLocation()
            if (locationResult.error != null) {
                _state.update {
                    it.copy(
                        weatherInfo = null,
                        isLoading = false,
                        error = UiError.Permission(locationResult.error, locationResult.isGranted)
                    )
                }
                return@launch
            }

            _state.update { state ->
                state.copy(
                    isLoading = true,
                    error = null
                )
            }
            val result = repository.getWeatherData(
                "${locationResult.data?.latitude},${locationResult.data?.longitude}"
            )
            _state.update { state ->
                state.copy(
                    weatherInfo = result.data,
                    isLoading = false,
                    error = UiError.Normal(result.uiText)
                )
            }
        }
    }

    sealed class UiError(val uiText: UiText? = null) {
        object InternetConnection : UiError()
        class Normal(error: UiText?) : UiError(error)
        data class Permission(
            private val error: UiText?,
            val hasPermission: Boolean
        ) : UiError(error)
    }
}