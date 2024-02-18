package com.ppidev.smartcube.ui.components.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ppidev.smartcube.ui.components.form.AppInput

@Composable
fun DialogJoinUserGroup(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    errorText: String,
    invitationCode: String,
    isLoading: Boolean,
    onChangeInvitationCode: (str: String) -> Unit,
    onJoinUserGroup: () -> Unit,
    onClose: () -> Unit
) {
    if (isOpen) {
        Dialog(onDismissRequest = {
            onClose()
        }) {
            Column(
                modifier = modifier
                    .shadow(
                        elevation = 4.dp,
                        spotColor = Color.Gray,
                        ambientColor = Color.Gray
                    )
                    .width(360.dp)
                    .clip(RoundedCornerShape(size = 8.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "Join to cluster",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    IconButton(onClick = {
                        onClose()
                    }) {
                        Icon(imageVector = Icons.Outlined.Close, contentDescription = "close")
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "Please input invitation code",
                    style = MaterialTheme.typography.bodyMedium,
                )

                Spacer(modifier = Modifier.size(24.dp))

                AppInput(
                    placeholder = "invitation token",
                    label = "Invitation Code",
                    text = invitationCode,
                    errorText = errorText,
                    onTextChanged = {
                        onChangeInvitationCode(it)
                    })

                Spacer(modifier = Modifier.size(32.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onJoinUserGroup() },
                    enabled = !isLoading
                ) {
                    Text(text = "Join Cluster")
                }
            }

            Spacer(modifier = Modifier.size(24.dp))
        }
    }
}