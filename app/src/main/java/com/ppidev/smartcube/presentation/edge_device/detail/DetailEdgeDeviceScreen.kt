package com.ppidev.smartcube.presentation.edge_device.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ppidev.smartcube.R
import com.ppidev.smartcube.domain.model.NotificationModel
import com.ppidev.smartcube.ui.components.card.CardDetailNotification
import com.ppidev.smartcube.ui.components.card.CardNotification
import com.ppidev.smartcube.ui.components.modal.DialogApp
import com.ppidev.smartcube.utils.dateFormat

@Composable
fun DetailEdgeDeviceScreen(
    state: DetailEdgeDeviceState,
    onEvent: (event: DetailEdgeDeviceEvent) -> Unit,
    navHostController: NavHostController,
    edgeDeviceId: UInt,
    edgeServerId: UInt,
    processId: Int,
    vendor: String,
    type: String
) {
    LaunchedEffect(Unit) {
        onEvent(
            DetailEdgeDeviceEvent.GetDetailDevice(
                serverId = edgeServerId,
                deviceId = edgeDeviceId
            )
        )
        onEvent(DetailEdgeDeviceEvent.SetDeviceInfo(edgeServerId, processId))
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (state.notificationId != null && state.notificationDetail != null) {
                CardDetailNotification(
                    title = state.notificationDetail.title,
                    date = state.notificationDetail.createdAt,
                    serverName = state.notificationDetail.edgeServerId.toString(),
                    deviceName = state.notificationDetail.edgeDeviceId.toString(),
                    description = state.notificationDetail.description,
                    imgUrl = state.notificationDetail.imageUrl,
                    riskLevel = state.notificationDetail.riskLevel,
                    objectLabel = state.notificationDetail.objectLabel,
                    type = state.notificationDetail.deviceType
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No Image", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.size(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                CardDetailDevice(
                    type = type,
                    vendorName = vendor,
                    serialNumber = "",
                    handleRestartDevice = {
                        onEvent(DetailEdgeDeviceEvent.RestartEdgeDevice)
                    },
                    handleStartDevice = {
                        onEvent(DetailEdgeDeviceEvent.StartEdgeDevice)
                    })

                Spacer(modifier = Modifier.size(32.dp))

                ListNotificationDetailDevice(
                    listNotification = state.notifications,
                    notificationActiveId = state.notificationId
                ) {
                    onEvent(DetailEdgeDeviceEvent.SetNotificationId(it))
                    onEvent(
                        DetailEdgeDeviceEvent.GetDetailNotification(
                            serverId = edgeServerId,
                            notificationId = it
                        )
                    )
                }
            }
        }

        if (state.isDialogMsgOpen) {
            DialogApp(
                message = if (state.isSuccess) "Success" else "Failed",
                contentMessage = state.messageDialog,
                isSuccess = state.isSuccess,
                onDismiss = { onEvent(DetailEdgeDeviceEvent.HandleCloseDialogMsg) },
                onConfirm = { onEvent(DetailEdgeDeviceEvent.HandleCloseDialogMsg) })
        }

        if (state.isLoading) {
            CircularProgressIndicator()
        }
    }
}


@Composable
private fun ListNotificationDetailDevice(
    listNotification: List<NotificationModel>,
    notificationActiveId: UInt? = null,
    onClick: (notificationId: UInt) -> Unit
) {
    if (listNotification.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(160.dp),
                painter = painterResource(id = R.drawable.thumb_empty),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "Empty Notification")
        }
    } else {
        LazyColumn {
            itemsIndexed(listNotification, key = { _, d -> d.id }) { _, item ->
                CardNotification(
                    title = item.title,
                    date = dateFormat(item.createdAt) ?: "-",
                    type = item.deviceType,
                    imgUrl = item.imageUrl,
                    server = item.edgeServerId.toString(),
                    device = item.edgeDeviceId.toString(),
                    isFocus = notificationActiveId == item.id.toUInt(),
                    onClick = {
                        onClick(item.id.toUInt())
                    }
                )
            }
        }
    }
}

@Composable
private fun CardDetailDevice(
    modifier: Modifier = Modifier,
    type: String,
    vendorName: String,
    serialNumber: String,
    handleRestartDevice: () -> Unit,
    handleStartDevice: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = vendorName, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = serialNumber,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.size(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                handleStartDevice()
            }, shape = RoundedCornerShape(4.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(imageVector = Icons.Filled.PlayCircle, contentDescription = "start device")
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(text = "Start")
                }
            }

            Spacer(modifier = Modifier.size(12.dp))

            Button(onClick = {
                handleRestartDevice()
            }, shape = RoundedCornerShape(4.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(imageVector = Icons.Filled.RestartAlt, contentDescription = "start device")
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(text = "Restart")
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CardDetailDevicePreview() {
    CardDetailDevice(
        type = "",
        vendorName = "",
        serialNumber = "",
        handleRestartDevice = { }) {

    }
}