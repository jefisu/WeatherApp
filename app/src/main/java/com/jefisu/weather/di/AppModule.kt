package com.jefisu.weather.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jefisu.weather.core.connectivity.ConnectivityObserver
import com.jefisu.weather.core.connectivity.NetworkConnectivityObserver
import com.jefisu.weather.data.DeviceLocationTracker
import com.jefisu.weather.data.WeatherRepositoryImpl
import com.jefisu.weather.data.remote.WeatherApi
import com.jefisu.weather.domain.LocationTracker
import com.jefisu.weather.domain.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl("http://api.weatherapi.com/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApi): WeatherRepository {
        return WeatherRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideLocationClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }

    @Provides
    @Singleton
    fun provideLocationTracker(
        locationClient: FusedLocationProviderClient,
        app: Application
    ): LocationTracker {
        return DeviceLocationTracker(locationClient, app)
    }

    @Provides
    @Singleton
    fun provideConnectivityObserver(app: Application): ConnectivityObserver {
        return NetworkConnectivityObserver(app)
    }
}