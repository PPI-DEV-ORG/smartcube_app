package com.ppidev.smartcube.presentation.edge_device.detail.camera

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import co.yml.charts.common.extensions.isNotNull
import com.ppidev.smartcube.R
import com.ppidev.smartcube.domain.model.NotificationModel
import com.ppidev.smartcube.ui.Screen
import com.ppidev.smartcube.ui.components.card.CardDetailNotification
import com.ppidev.smartcube.ui.components.card.CardNotification
import com.ppidev.smartcube.ui.components.modal.DialogApp
import com.ppidev.smartcube.ui.components.modal.DialogImageOverlay
import com.ppidev.smartcube.ui.components.shimmerEffect
import com.ppidev.smartcube.utils.isoDateFormatToStringDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailEdgeDeviceScreen(
    state: DetailEdgeDeviceCameraState,
    onEvent: (event: DetailEdgeDeviceCameraEvent) -> Unit,
    navHostController: NavHostController,
    edgeDeviceId: UInt,
    edgeServerId: UInt,
    processId: Int,
    mqttPubTopic: String,
    mqttSubTopic: String,
) {
    LaunchedEffect(Unit) {
        onEvent(
            DetailEdgeDeviceCameraEvent.GetDetailDeviceCamera(
                serverId = edgeServerId,
                deviceId = edgeDeviceId
            )
        )
        onEvent(DetailEdgeDeviceCameraEvent.SetDeviceInfoCamera(edgeServerId, processId))
    }

    val localDensity = LocalDensity.current
    val sheetController = rememberBottomSheetScaffoldState(
        SheetState(
            initialValue = SheetValue.Expanded,
            skipPartiallyExpanded = false,
            skipHiddenState = true
        )
    )

    BoxWithConstraints {
        var heightBottomSheet by remember {
            mutableStateOf(this.maxHeight * 0.7f)
        }
        var cardDetailHeight by remember {
            mutableStateOf(0.dp)
        }

        BottomSheetScaffold(
            scaffoldState = sheetController,
            sheetPeekHeight = cardDetailHeight,
            containerColor = Color.Transparent,
            sheetContent = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.height(with(localDensity) { heightBottomSheet })
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        CardDetailDevice(
                            modifier = Modifier.onGloballyPositioned {
                                cardDetailHeight = with(localDensity) {
                                    (it.size.height + 150).toDp()
                                }
                            },
                            isLoading = state.isLoadingDetailDevice,
                            vendorName = state.edgeDeviceDetail?.vendorName ?: "",
                            serialNumber = "",
                            handleRestartDevice = {
                                onEvent(DetailEdgeDeviceCameraEvent.RestartEdgeDeviceCamera)
                            },
                            handleStartDevice = {
                                onEvent(DetailEdgeDeviceCameraEvent.StartEdgeDeviceCamera)
                            },
                            navigateToUpdateDeviceScreen = {
                                navHostController.navigate(
                                    Screen.UpdateEdgeDevice.withArgs(
                                        "$edgeServerId",
                                        "$edgeDeviceId",
                                        mqttPubTopic,
                                        mqttSubTopic
                                    )
                                )
                            },
                            deleteDevice = {}
                        )

                        Spacer(modifier = Modifier.size(32.dp))

                        ListNotificationDetailDevice(
                            listNotification = state.notifications,
                            isLoading = state.isLoadingDetailDevice,
                            notificationActiveId = state.notificationId
                        ) {
                            if (state.notificationId != it) {
                                onEvent(DetailEdgeDeviceCameraEvent.SetNotificationId(it))
                                onEvent(
                                    DetailEdgeDeviceCameraEvent.GetDetailNotificationCamera(
                                        serverId = edgeServerId,
                                        notificationId = it
                                    )
                                )
                                heightBottomSheet = 330.dp
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
            ) {
                if (state.isLoadingDetailNotification) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(
                                RoundedCornerShape(4.dp)
                            )
                            .shimmerEffect()
                    ) {}
                    Spacer(modifier = Modifier.size(14.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .height(28.dp)
                            .padding(horizontal = 16.dp)
                            .clip(
                                RoundedCornerShape(4.dp)
                            )
                            .shimmerEffect()
                    ) {}
                } else {
                    if (state.notificationId != null && state.notificationDetail != null) {
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(animationSpec = tween(700)),
                            exit = fadeOut(animationSpec = tween(700))
                        ) {
                            CardDetailNotification(
                                title = state.notificationDetail.title,
                                date = state.notificationDetail.createdAt,
                                serverName = state.notificationDetail.edgeServerId.toString(),
                                deviceName = state.notificationDetail.edgeDeviceId.toString(),
                                description = state.notificationDetail.description,
                                imgUrl = state.notificationDetail.imageUrl,
                                riskLevel = state.notificationDetail.riskLevel,
                                objectLabel = state.notificationDetail.objectLabel,
                                type = state.notificationDetail.deviceType,
                                modifierImage = Modifier
                                    .height(210.dp)
                                    .clickable {
                                        onEvent(DetailEdgeDeviceCameraEvent.SetOpenImageOverlay(true))
                                    }
                            )
                        }
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
                }

                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }

    if (state.isDialogMsgOpen) {
        DialogApp(
            message = if (state.isSuccess) "Success" else "Failed",
            contentMessage = state.messageDialog,
            isSuccess = state.isSuccess,
            onDismiss = { onEvent(DetailEdgeDeviceCameraEvent.HandleCloseDialogMsg) },
            onConfirm = { onEvent(DetailEdgeDeviceCameraEvent.HandleCloseDialogMsg) })
    }

    if (state.notificationDetail.isNotNull() && state.isOpenImageOverlay) {
        DialogImageOverlay(imageUrl = state.notificationDetail?.imageUrl) {
            onEvent(DetailEdgeDeviceCameraEvent.SetOpenImageOverlay(false))
        }
    }

    if (state.isLoading) {
        CircularProgressIndicator()
    }
}


@Composable
private fun ListNotificationDetailDevice(
    listNotification: List<NotificationModel>,
    notificationActiveId: UInt? = null,
    isLoading: Boolean,
    onClick: (notificationId: UInt) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (isLoading) {
            items(4) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(
                            RoundedCornerShape(4.dp)
                        )
                        .shimmerEffect()
                ) {}
            }
        } else {
            if (listNotification.isEmpty()) {
                item {
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
                }
            } else {
                itemsIndexed(listNotification, key = { _, d -> d.id }) { _, item ->
                    CardNotification(
                        title = item.title,
                        date = isoDateFormatToStringDate(item.createdAt) ?: "-",
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
}

@Composable
private fun CardDetailDevice(
    modifier: Modifier = Modifier,
    vendorName: String,
    serialNumber: String,
    isLoading: Boolean,
    handleRestartDevice: () -> Unit,
    handleStartDevice: () -> Unit,
    navigateToUpdateDeviceScreen: () -> Unit,
    deleteDevice: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(48.dp)
                        .clip(
                            RoundedCornerShape(4.dp)
                        )
                        .shimmerEffect()
                ) {}
            } else {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = vendorName,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        IconButton(onClick = {
                            navigateToUpdateDeviceScreen()
                        }) {
                            Icon(imageVector = Icons.Filled.Edit, contentDescription = "edit")
                        }
                    }
                    Text(
                        text = serialNumber,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                IconButton(onClick = {
                    deleteDevice()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "delete",
                        tint = Color.Red
                    )
                }
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

}