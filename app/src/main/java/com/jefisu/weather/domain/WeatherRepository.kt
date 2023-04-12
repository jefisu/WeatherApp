package com.jefisu.weather.domain

import com.jefisu.weather.core.util.Resource
import com.jefisu.weather.domain.model.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(query: String): Resource<WeatherInfo>
}