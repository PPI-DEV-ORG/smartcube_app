package com.ppidev.smartcube.presentation.edge_device.detail

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.ppidev.smartcube.ui.Screen
import com.ppidev.smartcube.ui.components.modal.DialogApp

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
        onEvent(DetailEdgeDeviceEvent.SetDeviceInfo(edgeServerId, processId))
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(text = vendor)
                    Text(text = type)

                    Button(onClick = {
                        onEvent(DetailEdgeDeviceEvent.StartEdgeDevice)
                    }) {
                        Text(text = "Start")
                    }

                    Button(onClick = {
                        onEvent(DetailEdgeDeviceEvent.RestartEdgeDevice)
                    }) {
                        Text(text = "Restart")
                    }
                }

                IconButton(onClick = {
                    navHostController.navigate(Screen.UpdateEdgeDevice.withArgs("$edgeServerId", "$edgeDeviceId"))
                }) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "edit")
                }

                if (state.isDialogMsgOpen) {
                    DialogApp(
                        message = if (state.isSuccess) "Success" else "Failed",
                        contentMessage = state.messageDialog,
                        isSuccess = state.isSuccess,
                        onDismiss = { onEvent(DetailEdgeDeviceEvent.HandleCloseDialogMsg) },
                        onConfirm = { onEvent(DetailEdgeDeviceEvent.HandleCloseDialogMsg) })
                }
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator()
        }
    }
}