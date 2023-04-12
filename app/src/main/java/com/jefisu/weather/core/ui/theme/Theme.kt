package com.jefisu.weather.core.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

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
    val view = LocalView.current

    SideEffect {
        val window = (view.context as Activity).window

        window.statusBarColor = Color.Transparent.toArgb()
        window.navigationBarColor = Color.Transparent.toArgb()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
    }

    MaterialTheme(
        colors = ColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}