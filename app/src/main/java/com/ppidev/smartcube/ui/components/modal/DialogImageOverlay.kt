package com.ppidev.smartcube.ui.components.modal

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.ppidev.smartcube.R

@Composable
fun DialogImageOverlay(modifier: Modifier = Modifier, imageUrl: Any?, onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        Surface(
            color = Color.Transparent,
            modifier = modifier.width(480.dp),
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.thumb_flame),
                error = painterResource(id = R.drawable.thumb_error),
                modifier = Modifier
                    .width(480.dp)
                    .height(240.dp)
            )
        }
    }
}