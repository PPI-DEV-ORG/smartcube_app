package com.ppidev.smartcube.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ppidev.smartcube.R
import com.ppidev.smartcube.domain.model.TypeEdgeDevice
import com.ppidev.smartcube.ui.theme.Typography
import java.util.Locale


@Composable
fun CardNotification(
    modifier: Modifier = Modifier,
    title: String,
    date: String,
    type: String,
    imgUrl: String? = null,
    server: String,
    device: String,
    isFocus: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            modifier = Modifier.size(28.dp),
            painter = if (type == "camera") painterResource(id = R.drawable.ic_fire) else if (type == "sensor") painterResource(
                id = R.drawable.ic_sensor
            ) else painterResource(id = R.drawable.img_no_image),
            contentDescription = null,
            tint = if (type == "camera") {
                if (isFocus) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Red
                }
            } else if (type == "sensor") {
                if (isFocus) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Gray
                }
            } else {
                if (isFocus) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Black
                }
            }
        )

        Spacer(modifier = Modifier.size(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = buildAnnotatedString {
                val titleSpan =
                    Typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (isFocus) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onBackground
                        }
                    ).toSpanStyle()
                val subTitleSpan = Typography.bodyMedium.copy(
                    color = if (isFocus) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondary
                    }
                ).toSpanStyle()

                append(AnnotatedString(text = title, spanStyle = titleSpan))
                append(AnnotatedString(text = " - "))
                append(AnnotatedString(text = "$server - $device", spanStyle = subTitleSpan))
            }, maxLines = 1, overflow = TextOverflow.Ellipsis)

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = date,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        if (!imgUrl.isNullOrEmpty() && type == TypeEdgeDevice.CAMERA.typeName) {
            Box(
                modifier = Modifier
                    .size(width = 68.dp, height = 42.dp)
                    .clip(RoundedCornerShape(4.dp))
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                    model = imgUrl, contentDescription = null
                )

                if (isFocus) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFF505050).copy(alpha = 0.7f)
                            )
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.SignalCellularAlt,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CardDetailNotification(
    modifier: Modifier = Modifier,
    title: String,
    date: String,
    serverName: String,
    deviceName: String,
    description: String,
    imgUrl: String?,
    riskLevel: String?,
    objectLabel: String?,
    type: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        if (!imgUrl.isNullOrEmpty() && type == TypeEdgeDevice.CAMERA.typeName) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(186.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop, model = imgUrl, contentDescription = null,
                onError = { throwable ->
                    // Handle image loading failure
                    println("Image loading failed: ${throwable.result}")
                }
            )
        }

        Spacer(modifier = Modifier.size(14.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)

            if (!riskLevel.isNullOrEmpty() && type == TypeEdgeDevice.SENSOR.typeName) {
                val riskLevelLowerCase = riskLevel.lowercase(Locale.ROOT)
                Spacer(modifier = Modifier.size(12.dp))
                Box(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(8.dp)
                        )
                        .background(
                            color = if (riskLevelLowerCase == "high") {
                                Color.Red.copy(alpha = 0.7f)
                            } else if (riskLevelLowerCase == "medium") {
                                Color(0xFFFFBF00)
                            } else if (riskLevelLowerCase == "low") {
                                Color(0xFF77DD77)
                            } else {
                                Color.Gray.copy(alpha = 0.7f)
                            }
                        )
                        .padding(vertical = 4.dp, horizontal = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = riskLevel.uppercase(Locale.ROOT), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            } else if(!objectLabel.isNullOrEmpty() && type == TypeEdgeDevice.CAMERA.typeName) {
                Text(text = " - $objectLabel")
            }
        }

        Spacer(modifier = Modifier.size(6.dp))

        Text(
            text = date,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.size(14.dp))

        Text(
            text = "$serverName - $deviceName",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.DarkGray,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.DarkGray
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CardNotificationPreview() {
    CardDetailNotification(
        title = "",
        date = "",
        serverName = "",
        deviceName = "",
        description = "",
        type = "",
        imgUrl = "",
        riskLevel = "",
        objectLabel = ""
    )
//    CardNotification(title = "", date = "", type = "", imgUrl = "", server = "", device = "")
}