package com.jefisu.weather.core.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe(): Flow<Status>

    sealed class Status {
        object Available : Status()
        object Unavailable : Status()
    }
}