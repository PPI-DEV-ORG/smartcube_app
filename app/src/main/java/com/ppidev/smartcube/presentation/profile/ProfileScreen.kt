package com.ppidev.smartcube.presentation.profile

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HelpCenter
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.ppidev.smartcube.R


@Composable
fun ProfileScreen(
    state: ProfileState,
    onEvent: (event: ProfileEvent) -> Unit,
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                error = painterResource(id = R.drawable.thumb_error),
                model = state.user?.userAvatar,
                contentDescription = "avatar",
                modifier = Modifier
                    .shadow(ambientColor = Color.Gray, elevation = 24.dp, shape = CircleShape)
                    .size(110.dp)
                    .clip(CircleShape)
                    .border(6.dp, Color.Red, CircleShape)
            )

            Text(
                text = state.user?.userName ?: "-", style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(text = state.user?.userEmail ?: "-", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }

        Column(
            modifier = Modifier.padding(top = 60.dp)
        ) {
            CardSectionProfile(
                onClick = { },
                title = "Profile",
                icon = Icons.Filled.Person
            )

            CardSectionProfile(
                onClick = { },
                title = "Privacy",
                icon = Icons.Filled.Lock
            )

            CardSectionProfile(
                onClick = { },
                title = "Help Center",
                icon = Icons.Filled.HelpCenter
            )
        }
    }
}

@Composable
fun CardSectionProfile(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(imageVector = icon, contentDescription = title)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = title, modifier = Modifier.weight(1f))
        }
    }
}

@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(state = ProfileState(), onEvent = {}, navHostController = rememberNavController())
}