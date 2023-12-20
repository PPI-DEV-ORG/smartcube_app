package com.ppidev.smartcube.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp


@Composable
fun RoundedIcon(modifier: Modifier = Modifier, modifierIcon: Modifier = Modifier, icon: Painter) {
    Box(
        modifier = modifier
            .size(42.dp)
            .background(color = Color.LightGray, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = icon,
            contentDescription = "logo",
            modifier = modifierIcon
                .size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}