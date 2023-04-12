package com.jefisu.weather.presentation.home

import com.jefisu.weather.domain.model.WeatherInfo
import com.jefisu.weather.presentation.home.util.ForecastDay

data class HomeState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: HomeViewModel.UiError? = null,
    val daySelected: ForecastDay = ForecastDay.Today
)