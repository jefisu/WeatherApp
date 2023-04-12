package com.jefisu.weather.data.mapper

import com.jefisu.weather.data.remote.dto.HourDto
import com.jefisu.weather.domain.WeatherType
import com.jefisu.weather.domain.model.Hour
import java.time.LocalDateTime
import java.time.ZoneOffset

fun HourDto.toHour(): Hour {
    return Hour(
        localDateTime = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC),
        temperature = temperature,
        weatherType = WeatherType.values().first { it.code == condition.code }
    )
}