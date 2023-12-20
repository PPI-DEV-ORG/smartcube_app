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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ppidev.smartcube.data.remote.dto.EdgeServerItemDto
import com.ppidev.smartcube.ui.components.form.CustomSelectInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogInviteUser(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    errorSelectServer: String,
    selectedServerLabel: String,
    isLoading: Boolean,
    listEdgeServer: List<EdgeServerItemDto>,
    onInviteUser: () -> Unit,
    onChangeServer: (server: EdgeServerItemDto) -> Unit,
    onClose: () -> Unit
) {
    var expandedSelectServer by remember { mutableStateOf(false) }

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
                        text = "Invite Other User",
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
                    text = "Invite other user to cluster",
                    style = MaterialTheme.typography.bodyMedium,
                )

                Spacer(modifier = Modifier.size(24.dp))

                CustomSelectInput(
                    label = "Select Clusters",
                    expanded = expandedSelectServer,
                    value = selectedServerLabel,
                    errorText = errorSelectServer,
                    onExpandedChange = {
                        expandedSelectServer = !expandedSelectServer
                    },
                    listMenu = {
                        listEdgeServer.map {
                            DropdownMenuItem(
                                modifier = Modifier.fillMaxWidth(),
                                text = { Text(it.name) },
                                onClick = {
                                    onChangeServer(it)
                                    expandedSelectServer = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    },
                    onDismissRequest = {
                        expandedSelectServer = false
                    }
                )

                Spacer(modifier = Modifier.size(32.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onInviteUser() },
                    enabled = !isLoading
                ) {
                    Text(text = "Get Invitation Code")
                }
            }

            Spacer(modifier = Modifier.size(24.dp))
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun DialogInviteUserPreview() {
    DialogInviteUser(
        listEdgeServer = emptyList(),
        selectedServerLabel = "Label",
        onInviteUser = { },
        isOpen = true,
        isLoading = false,
        errorSelectServer = "",
        onChangeServer = { }
    ) {

    }
}