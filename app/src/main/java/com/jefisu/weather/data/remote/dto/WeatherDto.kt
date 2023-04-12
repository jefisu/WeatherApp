package com.jefisu.weather.data.remote.dto

import com.squareup.moshi.Json

data class WeatherDto(
    val location: Location,
    @field:Json(name = "current") val currentForecast: CurrentForecast,
    @field:Json(name = "forecast") val forecastNextDays: ForecastNextDays
)