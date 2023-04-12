package com.jefisu.weather.data.remote.dto

import com.squareup.moshi.Json

data class Location(
    @field:Json(name = "name") val city: String,
    val country: String
)