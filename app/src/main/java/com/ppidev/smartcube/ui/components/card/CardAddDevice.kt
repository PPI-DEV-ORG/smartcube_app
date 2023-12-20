package com.ppidev.smartcube.ui.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ppidev.smartcube.ui.theme.isLight

@Composable
fun CardAddDevice(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                spotColor = Color.Gray,
                ambientColor = Color.Gray
            )
            .fillMaxWidth()
            .height(172.dp)
            .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(size = 8.dp))
            .clickable {
                onClick()
            }
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp),
            )

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = "Add Device",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = if(MaterialTheme.colorScheme.isLight()) MaterialTheme.colorScheme.primary else Color.White ,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CardAddDevicePreview() {
    CardAddDevice(onClick = {})
}