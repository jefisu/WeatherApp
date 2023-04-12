package com.jefisu.weather.core.util

import android.location.Location

data class LocationResult(
    val data: Location?,
    val isGranted: Boolean,
    val error: UiText? = null
)