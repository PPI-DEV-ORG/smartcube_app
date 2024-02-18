package com.ppidev.smartcube.presentation.edge_server.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ppidev.smartcube.utils.EDGE_SERVER_ID_ARG
import com.ppidev.smartcube.ui.Screen
import com.ppidev.smartcube.ui.theme.isLight

@Composable
fun ListEdgeServerScreen(
    state: ListEdgeServerState,
    event: (event: ListEdgeServerEvent) -> Unit,
    navHostController: NavHostController
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isLoading)

    LaunchedEffect(Unit) {
        event(ListEdgeServerEvent.GetListEdgeServer)
    }


    SwipeRefresh(state = swipeRefreshState, onRefresh = {
        event(ListEdgeServerEvent.OnRefresh)
    },
        indicator = { stateRefresh, refreshTrigger ->
            SwipeRefreshIndicator(
                state = stateRefresh,
                refreshTriggerDistance = refreshTrigger,
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.primary
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 30.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Cluster", color = Color.White)

                IconButton(onClick = {
                    navHostController.navigate(Screen.FormAddEdgeServer.screenRoute)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add new edge server",
                        tint = Color.White
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (MaterialTheme.colorScheme.isLight()) Color.White else MaterialTheme.colorScheme.surface)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .padding(bottom = 100.dp),
                contentPadding = PaddingValues(
                    bottom = 120.dp,
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(
                    items = state.listEdgeServer,
                    key = { _, d -> "${d.id}_${d.name}" }
                ) { _, item ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (MaterialTheme.colorScheme.isLight()) Color.LightGray else MaterialTheme.colorScheme.surfaceVariant
                            )
                            .clickable {
                                navHostController.navigate(Screen.DetailEdgeServer.screenRoute + "?$EDGE_SERVER_ID_ARG=${item.id}")
                            }
                            .padding(16.dp)
                    ) {
                        Text(
                            text = item.name,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${item.vendor}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}