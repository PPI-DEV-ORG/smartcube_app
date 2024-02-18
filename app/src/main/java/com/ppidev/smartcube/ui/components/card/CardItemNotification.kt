package com.ppidev.smartcube.ui.components.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ppidev.smartcube.R
import com.ppidev.smartcube.domain.model.NotificationModel

@Composable
fun CardItemNotification(
    modifier: Modifier = Modifier,
    data: NotificationModel,
    onClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .width(329.dp)
            .height(150.dp)
            .clickable {
                if (onClick != null) {
                    onClick()
                }
            }
    ) {
        Card(
            shape = RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp, bottomStart = 14.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.DarkGray
            ),
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    Modifier.padding(start = 14.dp)
                ) {
                    Text(
                        text = data.title, style = TextStyle(
                            color = Color.White
                        )
                    )
                    Text(
                        text = data.description, style = TextStyle(
                            color = Color.White
                        )
                    )
                }
                Column {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(data.imageUrl).crossfade(true).build(),
                        contentDescription = "Object detected",
                        contentScale = ContentScale.Crop,
                        error = painterResource(id = R.drawable.img_no_image),
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(160.dp),
                    )
                }
            }
        }
    }
}