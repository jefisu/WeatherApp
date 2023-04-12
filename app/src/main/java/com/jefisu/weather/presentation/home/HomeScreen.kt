package com.jefisu.weather.presentation.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.jefisu.weather.R
import com.jefisu.weather.core.presentation.InternetUnavailableScreen
import com.jefisu.weather.core.ui.theme.LightYellow
import com.jefisu.weather.core.ui.theme.Shapes
import com.jefisu.weather.core.ui.theme.YankeesBlue
import com.jefisu.weather.core.ui.theme.spacing
import com.jefisu.weather.presentation.home.components.ForecastImage
import com.jefisu.weather.presentation.home.util.ForecastDay
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Context.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

@Composable
fun HomeScreen(
    state: HomeState,
    spacing: Dp,
    color: Color,
    onEvent: (HomeEvent) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val weatherInfo = state.weatherInfo
    val infos = listOf(
        stringResource(R.string.wind_speed) to "${weatherInfo?.windSpeed} km/h",
        stringResource(R.string.temperature) to "${weatherInfo?.temperature}°",
        stringResource(R.string.humidity) to "${weatherInfo?.humidity}%"
    )
    val date = remember {
        LocalDate.now().format(
            DateTimeFormatter.ofPattern("EEEE, d MMM")
        )
    }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.repeatOnLifecycle(
            state = Lifecycle.State.STARTED
        ) {
            if (weatherInfo == null) {
                onEvent(HomeEvent.LoadDataForecast)
            }
        }
    }

    LaunchedEffect(key1 = state.daySelected) {
        lazyListState.animateScrollToItem(0)
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = color)
        }
    }

    state.error?.let { error ->
        when (error) {
            is HomeViewModel.UiError.InternetConnection -> {
                InternetUnavailableScreen()
            }
            else -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = spacing)
                ) {
                    Text(
                        text = error.uiText?.asString().orEmpty(),
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                    Button(
                        shape = Shapes.small,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.background,
                            contentColor = color
                        ),
                        onClick = {
                            if (error is HomeViewModel.UiError.Permission && !error.hasPermission) {
                                context.openAppSettings()
                            } else {
                                onEvent(HomeEvent.LoadDataForecast)
                            }
                        }
                    ) {
                        Text(
                            text = stringResource(
                                if (error is HomeViewModel.UiError.Permission && !error.hasPermission) {
                                    R.string.grant_permission
                                } else {
                                    R.string.try_again
                                }
                            )
                        )
                    }
                }
            }
        }
    }

    if (weatherInfo != null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing)
            ) {
                Text(
                    text = date,
                    style = MaterialTheme.typography.body2,
                    color = Color.LightGray
                )
                Text(
                    text = "${weatherInfo.country}, ${weatherInfo.city}",
                    style = MaterialTheme.typography.body1,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing)
            ) {
                ForecastImage(imageRes = weatherInfo.weatherType.iconRes)
                Column {
                    Row {
                        Text(
                            text = "${weatherInfo.feelsLike}°",
                            fontSize = 80.sp,
                            color = color,
                            fontWeight = FontWeight.SemiBold,
                            softWrap = false,
                            modifier = Modifier.offset(x = 6.dp)
                        )
                        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                        Text(
                            text = "C",
                            color = color,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.offset(y = 16.dp)
                        )
                    }
                    Text(
                        text = weatherInfo.weatherType.description,
                        style = MaterialTheme.typography.body2,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .offset(y = (-10).dp)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = spacing)
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(CircleShape)
                    .background(YankeesBlue)
                    .padding(MaterialTheme.spacing.medium)
            ) {
                infos.forEach { info ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = info.first,
                            style = MaterialTheme.typography.body2,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = info.second,
                            style = MaterialTheme.typography.caption
                        )
                    }
                    if (info != infos.last()) {
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .fillMaxHeight()
                                .clip(CircleShape)
                                .background(MaterialTheme.colors.background)
                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing)
            ) {
                ForecastDay.values().forEach { day ->
                    val updateTransition = updateTransition(
                        targetState = state.daySelected == day,
                        label = ""
                    )
                    val dayBackgroundColor by updateTransition.animateColor(label = "") {
                        when {
                            it && color == MaterialTheme.colors.primaryVariant -> MaterialTheme.colors.primary
                            it -> MaterialTheme.colors.secondary
                            else -> YankeesBlue
                        }
                    }
                    val dayTextColor by updateTransition.animateColor(label = "") {
                        if (it) Color.White else color
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .height(35.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(dayBackgroundColor)
                            .clickable { onEvent(HomeEvent.SelectDay(day)) }
                    ) {
                        Text(
                            text = day.text,
                            fontSize = 12.sp,
                            color = dayTextColor
                        )
                    }
                }
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(spacing),
                contentPadding = PaddingValues(horizontal = spacing),
                state = lazyListState
            ) {
                items(
                    when (state.daySelected) {
                        ForecastDay.Today -> weatherInfo.forecastCurrentDayPerHour
                        ForecastDay.Tomorrow -> weatherInfo.forecastTomorrowPerHour
                        ForecastDay.Next7Days -> weatherInfo.forecastNextDays
                    }
                ) { hour ->
                    Column(
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(80.dp)
                            .clip(CircleShape)
                            .background(YankeesBlue)
                            .padding(MaterialTheme.spacing.small)
                    ) {
                        ForecastImage(
                            imageRes = hour.weatherType.iconRes,
                            imageSize = 60.dp
                        )
                        Text(
                            text = "${hour.temperature}°",
                            fontSize = 24.sp,
                            color = color,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${hour.localDateTime.hour}:00",
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.background,
                            modifier = Modifier
                                .drawBehind {
                                    drawOval(color = LightYellow)
                                }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}