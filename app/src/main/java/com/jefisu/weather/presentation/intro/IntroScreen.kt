package com.jefisu.weather.presentation.intro

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jefisu.weather.R
import com.jefisu.weather.core.ui.theme.*
import com.jefisu.weather.presentation.intro.components.HorizontalPagerIndicator
import com.jefisu.weather.presentation.intro.util.IntroPager
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroScreen(
    onClickNavigateToHome: () -> Unit
) {
    val spacingHorizontal = MaterialTheme.spacing.large
    val pagerState = rememberPagerState()
    val introPages = IntroPager.values()
    val scope = rememberCoroutineScope()

    val pageCurrent = remember {
        derivedStateOf {
            IntroPager.values()[pagerState.currentPage]
        }
    }

    val color by animateColorAsState(
        targetValue = pageCurrent.value.color
    )

    @Composable
    fun Header(
        onClickGoBackPager: () -> Unit,
        onClickSkipIntro: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                contentDescription = null,
                tint = color,
                modifier = Modifier
                    .alpha(if (pageCurrent.value == introPages.first()) 0f else 1f)
                    .clip(CircleShape)
                    .clickable { onClickGoBackPager() }
            )
            Text(
                text = stringResource(R.string.skip_intro),
                style = MaterialTheme.typography.body1,
                color = color,
                modifier = Modifier.clickable { onClickSkipIntro() }
            )
        }
    }

    @Composable
    fun Body(
        modifier: Modifier = Modifier
    ) {
        Box(
            modifier = modifier
        ) {
            HorizontalPager(
                state = pagerState,
                pageCount = introPages.size
            ) { page ->
                ScrollPagerItem(
                    introPager = introPages[page],
                    modifier = Modifier.padding(horizontal = spacingHorizontal)
                )
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
    @Composable
    fun BottomNavigation(
        onClickNextPager: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
        ) {
            HorizontalPagerIndicator(
                pagerState = pagerState,
                pageCount = introPages.size,
                activeColor = color
            )
            Surface(
                color = color,
                shape = CircleShape,
                modifier = Modifier.size(60.dp),
                onClick = onClickNextPager
            ) {
                Box(contentAlignment = Alignment.Center) {
                    val lastIntroPage = pageCurrent.value == introPages.last()
                    val lottieComposition by
                    rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.checkmark_lottie))

                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                        contentDescription = null,
                        tint = MaterialTheme.colors.background,
                        modifier = Modifier
                            .size(26.dp)
                            .scale(-1f, 1f)
                            .alpha(if (lastIntroPage) 0f else 1f)
                    )
                    LottieAnimation(
                        composition = lottieComposition,
                        isPlaying = lastIntroPage,
                        modifier = Modifier.alpha(if (lastIntroPage) 1f else 0f)
                    )
                }
            }
        }
    }

    Column {
        val scrollNextPage: () -> Unit = {
            scope.launch {
                pagerState.animateScrollToPage(pageCurrent.value.nextPagerIndex)
            }
        }

        Header(
            onClickGoBackPager = scrollNextPage,
            onClickSkipIntro = onClickNavigateToHome,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = spacingHorizontal)
        )
        Body(
            modifier = Modifier
                .weight(10f)
                .fillMaxWidth()
        )
        BottomNavigation(
            onClickNextPager = {
                if (pageCurrent.value == introPages.last()) {
                    onClickNavigateToHome()
                } else {
                    scrollNextPage()
                }
            },
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .padding(horizontal = spacingHorizontal)
        )
    }
}

@Composable
private fun ScrollPagerItem(
    introPager: IntroPager,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(Shapes.large)
                .background(introPager.backgroundColor)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(introPager.imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxHeight(0.80f)
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium + 8.dp))
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.SemiBold,
            color = introPager.color
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
        Text(
            text = stringResource(introPager.description),
            style = MaterialTheme.typography.body1,
            color = Color.White,
            textAlign = TextAlign.Justify,
            maxLines = 3
        )
    }
}

@Preview(device = Devices.PIXEL_4)
@Composable
fun PreviewIntroScreen() {
    Surface(color = YankeesBlue) {
        IntroScreen(
            onClickNavigateToHome = {}
        )
    }
}