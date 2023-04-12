package com.jefisu.weather.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jefisu.weather.core.util.Screen
import com.jefisu.weather.presentation.MainScreen
import com.jefisu.weather.presentation.intro.IntroScreen
import com.jefisu.weather.presentation.intro.IntroViewModel
import com.jefisu.weather.presentation.location_permission.LocationPermissionScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startScreen: Screen,
    modifier: Modifier = Modifier
) {
    val navigateTo: (Screen) -> Unit = { screen ->
        navController.backQueue.clear()
        navController.navigate(screen.route)
    }

    NavHost(
        navController = navController,
        startDestination = startScreen.route,
        modifier = modifier
    ) {
        composable(Screen.Intro.route) {
            val viewModel = hiltViewModel<IntroViewModel>()

            IntroScreen(
                onClickNavigateToHome = {
                    viewModel.skipInitialIntro()
                    navigateTo(Screen.LocationPermission)
                }
            )
        }
        composable(Screen.LocationPermission.route) {
            LocationPermissionScreen(
                onClickSkipPermission = { navigateTo(Screen.Main) }
            )
        }
        composable(Screen.Main.route) {
            MainScreen()
        }
    }
}