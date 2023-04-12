package com.jefisu.weather.data.mapper

import com.jefisu.weather.data.remote.dto.WeatherDto
import com.jefisu.weather.domain.WeatherType
import com.jefisu.weather.domain.model.WeatherInfo
import java.time.LocalDate

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val nextDays = forecastNextDays.forecastDays
        .flatMap { it.hours }
        .map { it.toHour() }

    val currentLocalDate = LocalDate.now()

    return WeatherInfo(
        city = location.city,
        country = location.country,
        windSpeed = currentForecast.wind,
        temperature = currentForecast.temperature,
        feelsLike = currentForecast.feelsLike.toInt(),
        humidity = currentForecast.humidity,
        forecastCurrentDayPerHour = nextDays.filter { hour ->
            hour.localDateTime.toLocalDate().isEqual(currentLocalDate)
        },
        forecastTomorrowPerHour = nextDays.filter { hour ->
            hour.localDateTime.toLocalDate().isAfter(currentLocalDate)
        },
        forecastNextDays = nextDays.sortedBy { it.localDateTime.toLocalDate() },
        weatherType = WeatherType.values().first { it.code == currentForecast.condition.code }
    )
}