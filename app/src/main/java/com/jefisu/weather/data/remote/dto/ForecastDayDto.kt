package com.jefisu.weather.data.remote.dto

import com.squareup.moshi.Json

data class ForecastDayDto(
    @field:Json(name = "hour") val hours: List<HourDto>
)