package com.jefisu.weather.data.remote

import com.jefisu.weather.BuildConfig
import com.jefisu.weather.data.remote.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(
        "forecast.json?key=${BuildConfig.api_key}&aqi=no&alerts=no"
    )
    suspend fun getForecastNextDays(
        @Query("q") query: String,
        @Query("days") days: Int = 7
    ): WeatherDto
}