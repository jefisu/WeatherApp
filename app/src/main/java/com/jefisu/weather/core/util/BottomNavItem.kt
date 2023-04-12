package com.jefisu.weather.core.util

import androidx.annotation.DrawableRes
import com.jefisu.weather.R

enum class BottomNavItem(
    @DrawableRes val iconRes: Int,
    val route: String
) {
    Home(
        iconRes = R.drawable.ic_home,
        route = "Home"
    ),
    Search(
        iconRes = R.drawable.ic_search,
        route = "Search"
    ),
    Locations(
        iconRes = R.drawable.ic_category,
        route = "Locations"
    ),
    Profile(
        iconRes = R.drawable.ic_user,
        route = "Profile"
    )
}