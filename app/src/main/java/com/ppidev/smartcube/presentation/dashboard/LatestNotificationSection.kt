package com.ppidev.smartcube.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ppidev.smartcube.R
import com.ppidev.smartcube.domain.model.NotificationModel
import com.ppidev.smartcube.ui.components.CardItemNotification
import com.ppidev.smartcube.ui.components.HeaderSection
import com.ppidev.smartcube.ui.components.shimmerEffect


@Composable
fun LatestNotificationSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    listNotification: List<NotificationModel> = emptyList()
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (isLoading) {
            items(4) {
                Box(
                    modifier = Modifier
                        .width(329.dp)
                        .height(150.dp)
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                        .shimmerEffect()
                ) {}
            }
        } else {
            itemsIndexed(listNotification, key = { _, d -> d.id }) { _, d ->
                CardItemNotification(
                    data = d
                )
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun PreviewListNotificationSection() {
    LatestNotificationSection(
        isLoading = false,
        listNotification = listOf(
            NotificationModel(
                title = "tset",
                description = "Test",
                id = 43,
                imageUrl = "test",
                isViewed = false
            ),
            NotificationModel(
                title = "tset",
                description = "Test",
                id = 43,
                imageUrl = "test",
                isViewed = false
            ),
            NotificationModel(
                title = "tset",
                description = "Test",
                id = 43,
                imageUrl = "test",
                isViewed = false
            )
        )
    )
}