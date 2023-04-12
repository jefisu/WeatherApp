package com.jefisu.weather.presentation.location_permission

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jefisu.weather.R
import com.jefisu.weather.core.ui.theme.Shapes
import com.jefisu.weather.core.ui.theme.YankeesBlue
import com.jefisu.weather.core.ui.theme.spacing

@Composable
fun LocationPermissionScreen(
    onClickSkipPermission: () -> Unit
) {
    var declinedPermission by rememberSaveable { mutableStateOf(0) }
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissionMap ->
            val isGranted = permissionMap.entries.any { it.value }
            if (!isGranted) {
                declinedPermission++
            } else {
                onClickSkipPermission()
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = MaterialTheme.spacing.large,
                vertical = MaterialTheme.spacing.medium
            )
    ) {
        Text(
            text = stringResource(R.string.skip_intro),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.secondaryVariant.copy(
                alpha = if (declinedPermission == 2) 1f else 0.4f
            ),
            modifier = Modifier
                .align(Alignment.End)
                .clickable { onClickSkipPermission() }
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 24.dp,
                alignment = Alignment.CenterVertically
            ),
            modifier = Modifier.weight(1f)
        ) {
            LocationIcon(size = 150.dp)
            Text(
                text = stringResource(R.string.permission_location),
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.SemiBold,
                softWrap = false
            )
            Text(
                text = stringResource(R.string.message_enable_location_permission),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
        }
        Button(
            enabled = declinedPermission < 2,
            shape = Shapes.small,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.secondaryVariant
            ),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                permissionLauncher.launch(permissions)
            }
        ) {
            Text(
                text = stringResource(R.string.allow_permission),
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun LocationIcon(size: Dp) {
    Box(contentAlignment = Alignment.Center) {
        Box {
            Image(
                painter = painterResource(R.drawable.map),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
                    .background(Color.Black.copy(0.4f))
            )
        }
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_location),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color(0xFFFE4675)),
            modifier = Modifier.size(size * 0.8f)
        )
    }
}

@Preview
@Composable
fun PreviewLocationPermissionScreen() {
    Surface(color = YankeesBlue) {
        LocationPermissionScreen(
            onClickSkipPermission = { }
        )
    }
}