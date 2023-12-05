package com.ppidev.smartcube.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import com.ppidev.smartcube.R
import com.ppidev.smartcube.ui.components.shimmerEffect

@Composable
fun UserSection(
    modifier: Modifier = Modifier,
    username: String = "Jhon doew",
    email: String = "Jhon@gmail.com",
    isLoading: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(24.dp)
                        .clip(
                            RoundedCornerShape(4.dp)
                        )
                        .shimmerEffect()
                ) {}

                Spacer(modifier = Modifier.size(3.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(16.dp)
                        .clip(
                            RoundedCornerShape(3.dp)
                        )
                        .shimmerEffect()
                ) {}
            } else {
                Text(
                    text = "Hi, $username",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.size(2.dp))
                Text(
                    text = email,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "logo",
            modifier = Modifier
                .size(30.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun UserSectionPreview() {
    UserSection(isLoading = true)
}