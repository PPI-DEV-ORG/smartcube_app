package com.ppidev.smartcube.ui.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ppidev.smartcube.R
import com.ppidev.smartcube.domain.model.TypeEdgeDevice

@Composable
fun CardItemDevice(
    modifier: Modifier = Modifier,
    deviceType: String,
    vendorName: String,
    status: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                spotColor = Color(0x40858585),
                ambientColor = Color(0x40858585)
            )
            .fillMaxWidth()
            .height(172.dp)
            .background(color = Color(0xFFFEFEFE), shape = RoundedCornerShape(size = 8.dp))
            .clickable {
                onClick()
            }
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(color = Color.LightGray, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = if (deviceType == TypeEdgeDevice.CAMERA.typeName) painterResource(id = R.drawable.ic_camera) else painterResource(
                        id = R.drawable.ic_sensor
                    ),
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(24.dp)
                )
            }

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = if (deviceType == TypeEdgeDevice.CAMERA.typeName) "CCTV" else "Sensor",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = vendorName,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            if (status) {
                Box(
                    modifier = Modifier
                        .border(
                            2.dp,
                            Color.Green,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 3.dp),
                ) {
                    Text(
                        text = "ON",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Green
                    )
                }
            } else {
                Text(
                    text = "OFF",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CardItemDevicePreview() {
    CardItemDevice(
        deviceType = "camera",
        vendorName = "IMOU",
        status = false,
        onClick = {}
    )
}