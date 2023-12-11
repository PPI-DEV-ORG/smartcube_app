package com.ppidev.smartcube.ui.components.card

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CardTypeDevice(
    modifier: Modifier = Modifier,
    name: String,
    onClick: () -> Unit,
    icon: @Composable () -> Unit
) {
    Column(modifier = modifier.clickable {
        onClick()
    }, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFF000000),
                    shape = RoundedCornerShape(size = 10.dp)
                )
                .width(60.dp)
                .height(60.dp)
                .padding(start = 11.dp, top = 11.dp, end = 11.dp, bottom = 11.dp),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }
        Spacer(modifier = Modifier.size(6.dp))
        Text(text = name, style = MaterialTheme.typography.titleSmall)
    }
}

@Composable
@Preview(showBackground = true)
private fun CardTypeDevicePreview() {
    CardTypeDevice(name = "CCTV", onClick = {}, icon = {
        Icon(imageVector = Icons.Filled.Camera, contentDescription = "camera")
    })
}