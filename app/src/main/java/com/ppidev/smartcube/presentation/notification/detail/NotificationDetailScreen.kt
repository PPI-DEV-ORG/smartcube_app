package com.ppidev.smartcube.presentation.notification.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.yml.charts.common.extensions.isNotNull
import coil.compose.AsyncImage
import com.ppidev.smartcube.R
import com.ppidev.smartcube.domain.model.TypeEdgeDevice
import com.ppidev.smartcube.ui.components.modal.DialogImageOverlay
import com.ppidev.smartcube.utils.isoDateFormatToStringDate
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationDetailScreen(
    state: NotificationDetailState,
    onEvent: (event: NotificationDetailEvent) -> Unit,
    notificationId: UInt,
    edgeServerId: UInt,
    deviceType: String,
    navHostController: NavHostController
) {
    LaunchedEffect(Unit) {
        onEvent(NotificationDetailEvent.GetDetailNotification(notificationId, edgeServerId))
    }
    val notificationDetail = state.notificationModel

    Column {
        TopAppBar(title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    navHostController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                }
                Text(text = state.notificationModel?.title ?: "")
            }
        })

        if (notificationDetail.isNotNull()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                if (deviceType == TypeEdgeDevice.CAMERA.typeName) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clickable {
                                onEvent(NotificationDetailEvent.SetOverlayImageStatus(true))
                            },
                        placeholder = painterResource(id = R.drawable.thumb_flame),
                        error = painterResource(id = R.drawable.thumb_error),
                        contentScale = ContentScale.Crop,
                        model = notificationDetail?.imageUrl,
                        contentDescription = null,
                        onError = { throwable ->
                            println("Image loading failed: ${throwable.result}")
                        },
                    )

                    Spacer(modifier = Modifier.size(14.dp))
                }

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = notificationDetail?.title ?: "-",
                            style = MaterialTheme.typography.titleMedium
                        )

                        if (!state.notificationModel?.riskLevel.isNullOrEmpty() && deviceType == TypeEdgeDevice.SENSOR.typeName) {
                            val riskLevelLowerCase =
                                notificationDetail?.riskLevel?.lowercase(Locale.ROOT)
                            Spacer(modifier = Modifier.size(12.dp))
                            Box(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(8.dp)
                                    )
                                    .background(
                                        color = when (riskLevelLowerCase) {
                                            "high" -> Color.Red.copy(alpha = 0.7f)
                                            "medium" -> Color(0xFFFFBF00)
                                            "low" -> Color(0xFF77DD77)
                                            else -> Color.Gray.copy(alpha = 0.7f)
                                        }
                                    )
                                    .padding(vertical = 4.dp, horizontal = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                notificationDetail?.riskLevel?.uppercase(Locale.ROOT)?.let {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                }
                            }
                        } else if (!notificationDetail?.objectLabel.isNullOrEmpty() && deviceType == TypeEdgeDevice.CAMERA.typeName) {
                            Text(
                                text = " - ${notificationDetail?.objectLabel}",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.size(4.dp))

                    Text(
                        text = isoDateFormatToStringDate(notificationDetail?.createdAt ?: ""),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.size(12.dp))

                    Text(
                        text = "${notificationDetail?.edgeServerId} - ${notificationDetail?.edgeDeviceId}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = notificationDetail?.description ?: "-",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }

    if (state.notificationModel.isNotNull() && state.isOpenImageOverlay) {
        DialogImageOverlay(imageUrl = state.notificationModel?.imageUrl) {
            onEvent(NotificationDetailEvent.SetOverlayImageStatus(false))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotificationDetailScreenPreview() {
    NotificationDetailScreen(
        state = NotificationDetailState(),
        onEvent = {},
        notificationId = (1).toUInt(),
        edgeServerId = 1.toUInt(),
        deviceType = "",
        navHostController = rememberNavController()
    )
}