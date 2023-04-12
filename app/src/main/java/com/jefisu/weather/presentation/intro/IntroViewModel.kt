package com.jefisu.weather.presentation.intro

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.jefisu.weather.core.util.WeatherConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val prefs: SharedPreferences
) : ViewModel() {

    fun skipInitialIntro() {
        prefs.edit()
            .putBoolean(WeatherConstants.SKIP_INTRO, true)
            .apply()
    }
}