package com.jefisu.weather

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.jefisu.weather.core.navigation.NavGraph
import com.jefisu.weather.core.ui.theme.WeatherTheme
import com.jefisu.weather.core.util.Screen
import com.jefisu.weather.core.util.WeatherConstants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()

            WeatherTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavGraph(
                        navController = navController,
                        startScreen = getStartScreen(),
                        modifier = Modifier
                            .statusBarsPadding()
                            .navigationBarsPadding()
                    )
                }
            }
        }
    }

    private fun getStartScreen(): Screen {
        val canSkipIntro = prefs.getBoolean(WeatherConstants.SKIP_INTRO, false)
        return if (canSkipIntro) Screen.Main else Screen.Intro
    }
}