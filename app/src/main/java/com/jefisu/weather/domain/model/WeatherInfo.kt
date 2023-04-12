package com.jefisu.weather.domain.model

import com.jefisu.weather.domain.WeatherType

data class WeatherInfo(
    val city: String,
    val country: String,
    val windSpeed: Float,
    val temperature: Float,
    val feelsLike: Int,
    val humidity: Int,
    val forecastCurrentDayPerHour: List<Hour>,
    val forecastTomorrowPerHour: List<Hour>,
    val forecastNextDays: List<Hour>,
    val weatherType: WeatherType
)
