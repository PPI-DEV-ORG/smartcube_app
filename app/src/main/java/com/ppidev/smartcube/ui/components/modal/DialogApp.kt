package com.ppidev.smartcube.ui.components.modal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SmsFailed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ppidev.smartcube.R


@Composable
fun DialogApp(
    message: String,
    contentMessage: String,
    isSuccess: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.95f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = message,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 20.sp
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (isSuccess) {
                        Icon(
                            modifier = Modifier.size(90.dp),
                            painter = painterResource(id = R.drawable.ic_check_circle),
                            contentDescription = message,
                            tint = Color.Unspecified
                        )
                    } else {
                        Icon(
                            modifier = Modifier.size(90.dp),
                            imageVector = Icons.Outlined.SmsFailed,
                            contentDescription = message,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                }

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = contentMessage,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 16.sp
                    )
                )

                Button(
                    onClick = {
                        onConfirm()
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
                ) {
                    Text(
                        text = "Confirm",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}
