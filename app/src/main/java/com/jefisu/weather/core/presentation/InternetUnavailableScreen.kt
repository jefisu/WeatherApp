package com.jefisu.weather.core.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jefisu.weather.R
import com.jefisu.weather.core.ui.theme.YankeesBlue
import com.jefisu.weather.core.ui.theme.spacing

@Composable
fun InternetUnavailableScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.spacing.large)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.internet_unavailable),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        Text(
            text = stringResource(R.string.you_re_offline),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.SemiBold,
            softWrap = false
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Text(
            text = stringResource(R.string.check_your_internet_connection),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewInternetUnavailableScreen() {
    Surface(color = YankeesBlue) {
        InternetUnavailableScreen()
    }
}