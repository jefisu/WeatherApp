package com.jefisu.weather.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.jefisu.weather.core.ui.theme.YankeesBlue
import com.jefisu.weather.core.ui.theme.spacing
import com.jefisu.weather.core.util.BottomNavItem
import com.jefisu.weather.presentation.home.HomeScreen
import com.jefisu.weather.presentation.home.HomeViewModel
import java.time.LocalTime

@Composable
fun MainScreen() {
    val spacingHorizontal = MaterialTheme.spacing.large

    var isNight by rememberSaveable { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current
    val color =
        if (isNight) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.secondaryVariant

    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.repeatOnLifecycle(
            state = Lifecycle.State.STARTED
        ) {
            val currentHour = LocalTime.now().hour
            if ((currentHour !in 6..17) != isNight) {
                isNight = !isNight
            }
        }
    }

    Column {
        Box(modifier = Modifier.weight(1f)) {
            val viewModel = hiltViewModel<HomeViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            HomeScreen(
                state = state,
                spacing = spacingHorizontal,
                color = color,
                onEvent = viewModel::onEvent
            )
        }
        BottomNavBar(
            spacing = spacingHorizontal,
            activeColor = color
        )
    }
}

@Composable
private fun BottomNavBar(
    spacing: Dp,
    activeColor: Color
) {
    val bottomNavItems = BottomNavItem.values()
    val disabledColor = activeColor.copy(alpha = ContentAlpha.disabled)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                start = spacing,
                end = spacing,
                bottom = 24.dp
            )
            .fillMaxWidth()
            .height(75.dp)
            .clip(CircleShape)
            .background(YankeesBlue)
            .padding(MaterialTheme.spacing.medium)
    ) {
        bottomNavItems.forEach { navItem ->
            val selected = navItem == BottomNavItem.Home
            val color = if (selected) activeColor else disabledColor

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(navItem.iconRes),
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier
                        .padding(top = if (selected) MaterialTheme.spacing.small else 0.dp)
                        .size(if (selected) 10.dp else 24.dp)
                )
                AnimatedVisibility(visible = selected) {
                    Text(
                        text = navItem.route,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = color,
                        modifier = Modifier.padding(top = MaterialTheme.spacing.extraSmall)
                    )
                }
            }
        }
    }
}