package com.ppidev.smartcube.presentation.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ppidev.smartcube.domain.model.NotificationModel
import com.ppidev.smartcube.ui.components.CardItemNotification


@Composable
fun NotificationListScreen() {
    val dummyNotificationModelList = listOf(
        NotificationModel(id = 1, imageUrl = "empty", isViewed = false, description = "desc", title = "title")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopCenter)
            .padding(horizontal = 16.dp).padding(top = 16.dp, bottom = 100.dp)
    ) {
        Text(text = "News Notifications", style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        ), modifier = Modifier.padding(bottom = 8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(8) {
                CardItemNotification(data = dummyNotificationModelList[0], modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationListScreenPreview() {
    NotificationListScreen()
}