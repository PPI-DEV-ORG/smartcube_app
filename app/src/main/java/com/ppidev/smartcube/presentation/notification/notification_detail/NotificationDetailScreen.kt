package com.ppidev.smartcube.presentation.notification.notification_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationDetailScreen(
    state: NotificationDetailState,
    onEvent: (event: NotificationDetailEvent) -> Unit,
    notificationId: UInt,
    navHostController: NavHostController
) {
    LaunchedEffect(Unit) {
        onEvent(NotificationDetailEvent.GetDetailNotification(notificationId))
    }

    Column {
        TopAppBar(title = {
            Text(text = state.notificationModel.title)
        })

        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {

            if (state.error.isNotEmpty()) {
                Text(text = state.error, color = Color.Red)
            }

            AsyncImage(
                model = state.notificationModel.imageUrl,
                contentDescription = "object detected"
            )

            Spacer(modifier = Modifier.size(22.dp))

            Text(text = state.notificationModel.title, fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.size(14.dp))

            Text(text = state.notificationModel.description)

            Spacer(modifier = Modifier.size(14.dp))


            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Waktu")
                    Spacer(modifier = Modifier.size(28.dp))
                    Text(text = "10:00")
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Object detected")
                    Spacer(modifier = Modifier.size(28.dp))
                    Text(text = "Fire")
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Confidence")
                    Spacer(modifier = Modifier.size(28.dp))
                    Text(text = "90%")
                }
            }
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
        navHostController = rememberNavController()
    )
}