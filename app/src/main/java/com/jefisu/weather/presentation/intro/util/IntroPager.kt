package com.jefisu.weather.presentation.intro.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.jefisu.weather.R
import com.jefisu.weather.core.ui.theme.BackgroundColdWeather
import com.jefisu.weather.core.ui.theme.BackgroundHotWeather
import com.jefisu.weather.core.ui.theme.LightBlue
import com.jefisu.weather.core.ui.theme.LightRed

enum class IntroPager(
    @DrawableRes val imageRes: Int,
    val backgroundColor: Color,
    @StringRes val description: Int,
    val color: Color,
    val nextPagerIndex: Int
) {
    ColdWeather(
        imageRes = R.drawable.cold_weather,
        backgroundColor = BackgroundColdWeather,
        description = R.string.description_intro,
        color = LightBlue,
        nextPagerIndex = 1
    ),
    HotWeather(
        imageRes = R.drawable.hot_weather,
        backgroundColor = BackgroundHotWeather,
        description = R.string.description_intro,
        color = LightRed,
        nextPagerIndex = 0
    )
}