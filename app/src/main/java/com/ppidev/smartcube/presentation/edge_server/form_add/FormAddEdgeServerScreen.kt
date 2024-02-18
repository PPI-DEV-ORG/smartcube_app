package com.ppidev.smartcube.presentation.edge_server.form_add

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ppidev.smartcube.utils.EDGE_SERVER_ACCESS_TOKEN
import com.ppidev.smartcube.utils.EDGE_SERVER_ID_ARG
import com.ppidev.smartcube.ui.Screen
import com.ppidev.smartcube.ui.components.TagLabel
import com.ppidev.smartcube.ui.components.form.AppInput
import com.ppidev.smartcube.ui.components.form.InputLabel
import com.ppidev.smartcube.ui.components.modal.DialogApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormAddEdgeServerScreen(
    state: FormAddEdgeServerState,
    onEvent: (event: FormAddEdgeServerEvent) -> Unit,
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        TopAppBar(title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    navHostController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                }
                Text(
                    text = "Add Cluster", style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                )
            }
        })

        Spacer(modifier = Modifier.size(44.dp))

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 20.dp)
        ) {
            TagLabel(modifier = Modifier.offset(y = (-24).dp),
                text = "Warning",
                backgroundColor = ButtonDefaults.buttonColors(Color.LightGray),
                textStyle = TextStyle(
                    color = Color.Red
                ),
                iconStart = true,
                icon = {
                    Icon(imageVector = Icons.Outlined.Warning, contentDescription = "warning")
                })

            Text(
                modifier = Modifier.padding(top = 32.dp, bottom = 19.dp),
                text = "Before adding devices, you should configure the multiprocessing script on your edge server.\n" +
                        "Please contact smartcube team to help you on configure edge server.",
                style = TextStyle(
                    textAlign = TextAlign.Justify,
                )
            )
        }

        Spacer(modifier = Modifier.size(32.dp))
        CardItemAddServer(
            titleTag = "Cluster",
            serverName = state.serverName,
            description = state.description,
            serverVendor = state.serverVendor,
            errorServerName = state.error.serverName,
            errorDescription = state.error.description,
            errorVendor = state.error.serverVendor,
            onServerNameChange = {
                onEvent(FormAddEdgeServerEvent.OnNameEdgeServerChange(it))
            },
            onDescriptionChange = {
                onEvent(FormAddEdgeServerEvent.OnDescriptionEdgeServerChange(it))
            },
            onServerVendorChange = {
                onEvent(FormAddEdgeServerEvent.OnVendorEdgeServerChange(it))
            }
        )

        Spacer(modifier = Modifier.size(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Button(
                onClick = {
                    navHostController.popBackStack()
                },
                shape = RoundedCornerShape(8),
                modifier = Modifier.weight(1f),
                elevation = null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            ) {
                Text(text = "Cancel")
            }

            Spacer(modifier = Modifier.size(36.dp))

            Button(
                onClick = {
                    onEvent(FormAddEdgeServerEvent.HandleAddEdgeServer)
                },
                shape = RoundedCornerShape(8),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
            ) {
                Text(text = "Save")
            }
        }

        state.isSuccess?.let {
            DialogApp(
                message = if (it) "Success" else "Failed",
                contentMessage = state.message,
                isSuccess = it,
                onDismiss = {
                },
                onConfirm = {
                    onEvent(FormAddEdgeServerEvent.HandleCloseDialog {
                        if (it) {
                            navHostController.popBackStack()
                            navHostController.navigate(Screen.DetailEdgeServer.screenRoute + "?$EDGE_SERVER_ID_ARG=${state.edgeServerId}" + "&$EDGE_SERVER_ACCESS_TOKEN=${state.edgeServerAccessToken}")
                        }
                    })
                }
            )
        }
    }
}

@Composable
private fun CardItemAddServer(
    titleTag: String,
    serverName: String,
    description: String,
    serverVendor: String,
    errorServerName: String,
    errorDescription: String,
    errorVendor: String,
    onServerNameChange: (str: String) -> Unit,
    onDescriptionChange: (str: String) -> Unit,
    onServerVendorChange: (str: String) -> Unit
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 20.dp)
    ) {
        TagLabel(modifier = Modifier.offset(y = (-24).dp), text = titleTag)

        Column(
            modifier = Modifier.padding(vertical = 32.dp)
        ) {
            AppInput(
                label = "Cluster Name",
                customLabel = {
                    InputLabel(label = "Cluster Name", isRequired = true)
                },
                placeholder = "Enter cluster name",
                text = serverName,
                errorText = errorServerName,
                onTextChanged = { onServerNameChange(it) })
            AppInput(
                customLabel = {
                    InputLabel(label = "Server vendor", isRequired = true)
                },
                text = serverVendor,
                placeholder = "Enter server vendor name",
                errorText = errorVendor,
                onTextChanged = { onServerVendorChange(it) })
            AppInput(
                label = "Description",
                placeholder = "Enter description cluster",
                text = description,
                errorText = errorDescription,
                onTextChanged = { onDescriptionChange(it) })
        }
    }
}