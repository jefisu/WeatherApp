package com.jefisu.weather.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

val ColorPalette = darkColors(
    primary = Blue,
    primaryVariant = LightBlue,
    secondary = Red,
    secondaryVariant = LightRed,
    background = DarkBlue,
    surface = DarkBlue
)

@Composable
fun WeatherTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = ColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}