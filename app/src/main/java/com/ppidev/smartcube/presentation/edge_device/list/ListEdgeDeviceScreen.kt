package com.ppidev.smartcube.presentation.edge_device.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Videocam
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(
                    start = 12.dp,
                    top = 16.dp,
                    end = 12.dp,
                    bottom = 16.dp
                ),
            ) {
                itemsIndexed(
                    items = state.edgeDevicesInfo.devices,
                    key = { _, d -> d.id }
                ) { _, item ->
                    CardSquareDevice(name = item.vendorName, type = item.type) {
                        navHostController.navigate("")
                    }
                }
            }
        }
    }
}


@Composable
fun CardSquareDevice(
    modifier : Modifier = Modifier,
    name: String,
    type: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (type == "camera") {
            Icon(
                imageVector = Icons.Filled.Videocam,
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
        } else if (type == "sensor") {
            Icon(
                imageVector = Icons.Filled.Sensors,
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
        }

        Text(text = name)
    }
}


@Preview
@Composable
fun CardSquareDevicePreview() {
    CardSquareDevice(name = "Cam 1", type = "camera") {

    }
}