package com.ppidev.smartcube.ui.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeatherCardV2(
    tempC: String = "0° C",
    feelsLike: String = "...",
    location: String = "-",
    condition: String = "-"
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp)
            .clip(RoundedCornerShape(4.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFD1A3DD),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = condition, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(text = location, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(text = tempC, fontSize = 24.sp, fontWeight = FontWeight.Medium)
                Text(
                    text = "Feels like $feelsLike",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun WeatherCardPreview() {
    WeatherCardV2()
}