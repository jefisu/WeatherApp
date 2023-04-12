package com.jefisu.weather.presentation.home

import com.jefisu.weather.presentation.home.util.ForecastDay

sealed class HomeEvent {
    object LoadDataForecast : HomeEvent()
    data class SelectDay(val day: ForecastDay) : HomeEvent()
}
