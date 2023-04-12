package com.jefisu.weather.core.util

sealed class Screen(val route: String) {
    object Intro : Screen("intro_screen")
    object LocationPermission : Screen("location_permission_screen")
    object Main : Screen("main_screen")
}