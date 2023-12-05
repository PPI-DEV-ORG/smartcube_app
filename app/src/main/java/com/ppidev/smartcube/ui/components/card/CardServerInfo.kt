package com.ppidev.smartcube.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ppidev.smartcube.presentation.edge_server.detail.AnimatedCircularProgressIndicator
import com.ppidev.smartcube.ui.theme.Purple40

@Composable
fun CardServerInfo(
    modifier: Modifier = Modifier,
    avgCpuTemp: String,
    totalRam: String,
    fanSpeed: String,
    upTime: String,
    ramUsage: Int = 0
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF8F8F8))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Server Info",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = "Processor temp (avg): $avgCpuTemp",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Total Ram Space: $totalRam",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "Fan Speed: $fanSpeed", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "Up time: $upTime", style = MaterialTheme.typography.bodySmall)
            }

            AnimatedCircularProgressIndicator(
                currentValue = ramUsage,
                maxValue = 100,
                progressBackgroundColor = Color.LightGray,
                progressIndicatorColor = Purple40,
                midColor = Color.Red,
                midValue = 50
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CardServerInfoPreview() {
    CardServerInfo(
        avgCpuTemp = "70 C",
        totalRam = "80 GB",
        fanSpeed = "3000 Rpm",
        upTime = "10h 5min"
    )
}