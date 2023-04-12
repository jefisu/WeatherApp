package com.jefisu.weather.data.remote.dto

import com.squareup.moshi.Json

data class ForecastNextDays(
    @field:Json(name = "forecastday") val forecastDays: List<ForecastDayDto>
)