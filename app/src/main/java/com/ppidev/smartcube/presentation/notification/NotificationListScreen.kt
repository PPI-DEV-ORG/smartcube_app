package com.ppidev.smartcube.presentation.notification

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ppidev.smartcube.ui.components.card.CardDetailNotification
import com.ppidev.smartcube.ui.components.card.CardNotification
import com.ppidev.smartcube.ui.components.shimmerEffect
import com.ppidev.smartcube.utils.dateFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationListScreen(
    state: NotificationState,
    onEvent: (event: NotificationEvent) -> Unit,
    navHostController: NavHostController
) {
    LaunchedEffect(Unit) {
        onEvent(NotificationEvent.GetListNotification)
    }

    Column {
        TopAppBar(title = {
            Text(
                text = "Notifications", style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                ), modifier = Modifier.padding(bottom = 8.dp)
            )
        })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 100.dp)
        ) {

            AnimatedVisibility(
                visible = state.notificationId != null && state.detailNotification != null,
            ) {
                CardDetailNotification(
                    title = state.detailNotification?.title.orEmpty(),
                    date = dateFormat(state.detailNotification?.createdAt ?: "") ?: "-",
                    serverName = "${state.detailNotification?.edgeServerId}",
                    deviceName = "${state.detailNotification?.edgeDeviceId}",
                    description = state.detailNotification?.description.orEmpty(),
                    type = state.detailNotification?.deviceType.orEmpty(),
                    imgUrl = state.detailNotification?.imageUrl,
                    riskLevel = state.detailNotification?.riskLevel,
                    objectLabel = state.detailNotification?.objectLabel
                )
            }

            Spacer(modifier = Modifier.size(32.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (state.isLoading) {
                    items(4) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(
                                    RoundedCornerShape(10.dp)
                                )
                                .shimmerEffect()
                        ) {}
                    }
                } else {
                    if (state.notifications.isEmpty()) {
                        item {
                            Text(text = "Empty Notification")
                        }
                    } else {
                        itemsIndexed(state.notifications, key = { _, d -> d.id }) { _, item ->
                            CardNotification(
                                title = item.title,
                                date = dateFormat(item.createdAt) ?: "-",
                                type = item.deviceType,
                                imgUrl = item.imageUrl,
                                server = "",
                                device = "",
                                isFocus = state.notificationId == item.id.toUInt(),
                                onClick = {
                                    onEvent(NotificationEvent.SetNotificationId(item.id.toUInt()))
                                    onEvent(
                                        NotificationEvent.GetNotificationDetail(
                                            item.id.toUInt(),
                                            item.edgeServerId
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationListScreenPreview() {
    NotificationListScreen(
        state = NotificationState(),
        onEvent = {},
        navHostController = rememberNavController()
    )
}