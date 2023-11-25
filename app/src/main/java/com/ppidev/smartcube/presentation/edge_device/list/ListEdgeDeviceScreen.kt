package com.ppidev.smartcube.presentation.edge_device.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ppidev.smartcube.ui.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListEdgeDeviceScreen(
    state: ListEdgeDeviceState,
    onEvent: (event: ListEdgeDeviceEvent) -> Unit,
    navHostController: NavHostController
) {

    LaunchedEffect(Unit) {
        onEvent(ListEdgeDeviceEvent.GetLstEdgeServer)
    }

    LaunchedEffect(key1 = state.serverId) {
        if (state.serverId != null) {
            onEvent(ListEdgeDeviceEvent.GetLstEdgeDevice(state.serverId))
        }
    }

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(title = {
            IconButton(onClick = {
                navHostController.navigate(Screen.FormAddEdgeDevice.screenRoute + "/${state.serverId}")
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "add devices")
            }
        })
        if (state.listEdgeServer.isNotEmpty()) {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                edgePadding = 0.dp
            ) {
                state.listEdgeServer.forEachIndexed { tabIndex, tab ->
                    Tab(
                        selected = selectedTabIndex == tabIndex,
                        onClick = {
                            onEvent(ListEdgeDeviceEvent.SetEdgeServerId(tab.id))
                            selectedTabIndex = tabIndex
                        },
                        text = {
                            Text(text = "${tab.name}")
                        }
                    )
                }
            }
        }

        if (state.edgeDevicesInfo?.devices?.isNotEmpty() == true) {
            LazyColumn {
                itemsIndexed(
                    items = state.edgeDevicesInfo.devices,
                    key = { _, d -> d.id }
                ) { _, item ->
                    Text(text = item.vendorName)
                }
            }
        }
    }
}