package com.jefisu.weather.presentation.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ForecastImage(
    @DrawableRes imageRes: Int,
    imageSize: Dp = 180.dp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(imageSize)
            .clip(CircleShape)
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(0.15f),
                        Color.White.copy(0.01f)
                    )
                )
            )
    ) {
        Image(
            imageVector = ImageVector.vectorResource(imageRes),
            contentDescription = null,
            modifier = Modifier.size(imageSize * 0.75f)
        )
    }
}