package com.jefisu.weather.data.remote.dto

import com.squareup.moshi.Json

data class CurrentForecast(
    @field:Json(name = "temp_c") val temperature: Float,
    val condition: Condition,
    @field:Json(name = "wind_kph") val wind: Float,
    val humidity: Int,
    @field:Json(name = "feelslike_c") val feelsLike: Float
)
