package com.jefisu.weather.domain.model

import com.jefisu.weather.domain.WeatherType
import java.time.LocalDateTime

data class Hour(
    val localDateTime: LocalDateTime,
    val temperature: Float,
    val weatherType: WeatherType
)