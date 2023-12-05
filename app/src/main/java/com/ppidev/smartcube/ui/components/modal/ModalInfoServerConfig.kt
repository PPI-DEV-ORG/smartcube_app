package com.ppidev.smartcube.ui.components.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ppidev.smartcube.R
import com.ppidev.smartcube.ui.components.RoundedIcon


@Composable
fun ModalInfoServerConfig(
    modifier: Modifier = Modifier,
    publishTopic: String,
    subscribeTopic: String,
    edgeServerToken: String,
    onClose: () -> Unit
) {
    Dialog(
        onDismissRequest = { },
    ) {
        Box(
            modifier = modifier
                .shadow(
                    elevation = 4.dp,
                    spotColor = Color(0x40535353),
                    ambientColor = Color(0x40535353)
                )
                .width(358.dp)
                .height(638.dp)
                .background(color = Color.White, shape = RoundedCornerShape(size = 8.dp))
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "Success added new server",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.size(28.dp))
                Text(
                    text = "Please config your server script at environment file before close this modal popup",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "(Edge Server Token only show once)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Red
                )

                Spacer(modifier = Modifier.size(24.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RoundedIcon(icon = painterResource(id = R.drawable.ic_upload))
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Mqtt (Publish Topic)")
                        Text(
                            text = publishTopic,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.ContentCopy,
                            contentDescription = "copy",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.size(20.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RoundedIcon(
                        icon = painterResource(id = R.drawable.ic_upload),
                        modifier = Modifier.rotate(180f)

                    )
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Mqtt (Subscribe Topic)")
                        Text(
                            text = subscribeTopic,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.ContentCopy,
                            contentDescription = "copy",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.size(28.dp))

                Text(text = "Edge Server Token", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.size(8.dp))

                Row {
                    Text(text = edgeServerToken, modifier = Modifier.weight(1f), color = Color.Gray)
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.ContentCopy,
                            contentDescription = "copy",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.size(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(modifier = Modifier
                        .width(113.dp)
                        .height(32.dp),
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        onClick = {
                            onClose()
                        }
                    ) {
                        Text(text = "Close", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ModalInfoServerConfigPreview() {
    ModalInfoServerConfig(
        publishTopic = "pub-Mfkanf1",
        subscribeTopic = "sub-Mfkanf1",
        edgeServerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEwMjY1LCJlbWFpbCI6ImhxcG50cUBobGRyaXZlLmNvbSIsInVzZXJuYW1lIjoicHJhc3MiLCJpYXQiOjE3MDE0NDMzNDEsImV4cCI6MTcwMjA0ODE0MX0.J9ELK2UUdZKyBGUspxU78gem1XJnPDX5vVL1lw0rgos",
        onClose = {}
    )
}