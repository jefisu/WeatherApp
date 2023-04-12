package com.jefisu.weather.presentation.intro.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerIndicator(
    pagerState: PagerState,
    pageCount: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    inactiveColor: Color = activeColor.copy(ContentAlpha.disabled),
    indicatorSize: Dp = 8.dp,
    spacing: Dp = indicatorSize,
    indicatorShape: Shape = CircleShape
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        val offsetIntPart = pagerState.currentPageOffsetFraction.toInt()
        val offsetFractionalPart = pagerState.currentPageOffsetFraction - offsetIntPart

        val currentPage = pagerState.currentPage + offsetIntPart
        val targetPage =
            if (pagerState.currentPageOffsetFraction < 0) currentPage - 1 else currentPage + 1

        val currentPageSize = indicatorSize * (1 + (1 - abs(offsetFractionalPart)))
        val targetPageSize = indicatorSize * (1 + abs(offsetFractionalPart))

        repeat(pageCount) { index ->
            val updateTransition = updateTransition(targetState = index, label = "")
            val sizeAnim by updateTransition.animateDp(label = "") { page ->
                when (page) {
                    currentPage -> currentPageSize
                    targetPage -> targetPageSize
                    else -> indicatorSize
                }
            }
            val colorAnim by updateTransition.animateColor(label = "") { page ->
                if (page == currentPage) activeColor else inactiveColor
            }

            Box(
                modifier = Modifier
                    .size(sizeAnim)
                    .clip(indicatorShape)
                    .background(colorAnim)
            )
        }
    }
}