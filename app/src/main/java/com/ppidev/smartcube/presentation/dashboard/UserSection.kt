package com.ppidev.smartcube.presentation.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ppidev.smartcube.R

@Composable
fun UserSection(
    modifier: Modifier = Modifier,
    username: String = "Jhon doew",
    email: String = "Jhon@gmail.com",
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 55.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = "photo",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Hi, $username", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 18.sp)
            Text(text = email, fontSize = 14.sp)
        }

//        IconButton(onClick = { /*TODO*/ }) {
//            Canvas(
//                modifier = Modifier.size(44.dp),
//                onDraw = {
//                    drawCircle(color = Color.LightGray)
//                }
//            )
//            Icon(
//                imageVector = Icons.Outlined.Notifications,
//                contentDescription = "notification"
//            )
//        }

    }
}


@Preview(showBackground = true)
@Composable
fun UserSectionPreview() {
    UserSection()
}