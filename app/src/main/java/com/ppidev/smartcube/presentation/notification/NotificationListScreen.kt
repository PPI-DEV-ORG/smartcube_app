package com.ppidev.smartcube.presentation.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.yml.charts.common.extensions.isNotNull
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ppidev.smartcube.ui.Screen
import com.ppidev.smartcube.ui.components.card.CardNotification
import com.ppidev.smartcube.ui.components.modal.DialogImageOverlay
import com.ppidev.smartcube.ui.components.shimmerEffect
import com.ppidev.smartcube.utils.isoDateFormatToStringDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationListScreen(
    state: NotificationState,
    onEvent: (event: NotificationEvent) -> Unit,
    navHostController: NavHostController
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isLoading)

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

        SwipeRefresh(state = swipeRefreshState, onRefresh = {
            onEvent(NotificationEvent.OnRefresh)
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                if (state.isLoading) {
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
                    if (state.notifications.isEmpty()) {
                        item {
                            Text(text = "Empty Notification")
                        }
                    } else {
                        itemsIndexed(
                            state.notifications,
                            key = { _, d -> d.id }) { _, item ->
                            CardNotification(
                                title = item.title,
                                date = isoDateFormatToStringDate(item.createdAt) ?: "-",
                                type = item.deviceType,
                                imgUrl = item.imageUrl,
                                server = "",
                                device = "",
                                isFocus = false,
                                onClick = {
                                    navHostController.navigate(
                                        Screen.DetailNotification.withArgs(
                                            "${item.id}",
                                            "${item.edgeServerId}",
                                            item.deviceType
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

    if (state.detailNotification.isNotNull() && state.isOpenImageOverlay) {
        DialogImageOverlay(imageUrl = state.detailNotification?.imageUrl) {
            onEvent(NotificationEvent.SetOpenImageOverlay(false))
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