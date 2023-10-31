package com.ppidev.smartcube.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ppidev.smartcube.R
import com.ppidev.smartcube.domain.model.DeviceConfigModel
import com.ppidev.smartcube.ui.components.HeaderSection

//@Composable
//fun ListCameraSection(modifier: Modifier = Modifier, listCamera: List<DeviceConfigModel>) {
//    Column(
//        modifier = modifier
//            .fillMaxWidth()
//    ) {
//        HeaderSection(title = "List cameras", isShowMoreExist = true, onClickShowMore = {})
//
//        Column(
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            listCamera.map {
//                CardCameraItem(type = it.type, deviceLocation = it.additionalInfo.deviceLocation)
//            }
//        }
//    }
//}


@Composable
fun CardCameraItem(type: String, deviceLocation: String) {
    Card(
        Modifier.height(60.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = type, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(
                    text = deviceLocation,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column(
                modifier = Modifier
                    .height(44.dp)
                    .width(44.dp),
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_camera),
                            contentDescription = "description"
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CardCameraItemPreview() {
    CardCameraItem(type = "Camera", deviceLocation = "Indonesia")
}