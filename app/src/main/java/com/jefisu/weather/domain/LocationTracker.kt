package com.jefisu.weather.domain

import com.jefisu.weather.core.util.LocationResult

interface LocationTracker {
    suspend fun getCurrentLocation(): LocationResult
}