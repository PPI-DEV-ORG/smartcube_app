package com.ppidev.smartcube.presentation.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.ppidev.smartcube.ui.Screen
import com.ppidev.smartcube.ui.components.card.CardItemNotification
import com.ppidev.smartcube.ui.components.shimmerEffect


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
                text = "News Notifications", style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                ), modifier = Modifier.padding(bottom = 8.dp)
            )
        })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp, bottom = 100.dp)
        ) {
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
                        itemsIndexed(state.notifications, key = { _, d -> d.id }) { _, d ->
                            CardItemNotification(
                                data = d,
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    navHostController.navigate(Screen.DetailNotification.screenRoute + "/${d.id}")
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