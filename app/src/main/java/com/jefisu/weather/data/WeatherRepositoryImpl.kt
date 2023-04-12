package com.jefisu.weather.data

import com.jefisu.weather.R
import com.jefisu.weather.core.util.Resource
import com.jefisu.weather.core.util.UiText
import com.jefisu.weather.data.mapper.toWeatherInfo
import com.jefisu.weather.data.remote.WeatherApi
import com.jefisu.weather.domain.WeatherRepository
import com.jefisu.weather.domain.model.WeatherInfo
import java.io.IOException
import retrofit2.HttpException

class WeatherRepositoryImpl(
    private val api: WeatherApi
) : WeatherRepository {

    override suspend fun getWeatherData(query: String): Resource<WeatherInfo> {
        return try {
            val data = api.getForecastNextDays(query)
            Resource.Success(data.toWeatherInfo())
        } catch (e: IOException) {
            Resource.Error(UiText.unknownError())
        } catch (e: HttpException) {
            Resource.Error(
                UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }
}