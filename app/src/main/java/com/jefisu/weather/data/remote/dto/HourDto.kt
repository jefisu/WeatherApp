package com.jefisu.weather.data.remote.dto

import com.squareup.moshi.Json

data class HourDto(
    @field:Json(name = "time_epoch") val timestamp: Long,
    @field:Json(name = "temp_c") val temperature: Float,
    val condition: Condition
)