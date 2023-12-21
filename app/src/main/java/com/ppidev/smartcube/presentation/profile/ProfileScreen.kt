package com.ppidev.smartcube.presentation.profile

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.HelpCenter
import androidx.compose.material.icons.filled.JoinFull
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.ppidev.smartcube.R
import com.ppidev.smartcube.ui.Screen
import com.ppidev.smartcube.ui.components.form.CustomInputField
import com.ppidev.smartcube.ui.components.modal.DialogInviteUser
import com.ppidev.smartcube.ui.components.modal.DialogJoinUserGroup
import com.ppidev.smartcube.ui.components.modal.SimpleAlertDialog

@Composable
fun ProfileScreen(
    state: ProfileState,
    onEvent: (event: ProfileEvent) -> Unit,
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    LaunchedEffect(Unit) {
        onEvent(ProfileEvent.GetUserProfile)
        onEvent(ProfileEvent.GetListServer)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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

            Text(
                text = state.user?.userEmail ?: "-",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        Column(
            modifier = Modifier.padding(top = 60.dp)
        ) {
            CardSectionProfile(
                onClick = {
                    onEvent(ProfileEvent.SetStatusOpenDialogInviteUser(true))
                },
                title = "Invite User",
                icon = Icons.Filled.SupervisedUserCircle
            )

            CardSectionProfile(
                onClick = {
                    onEvent(ProfileEvent.SetStatusOpenDialogJoinUser(true))
                },
                title = "Insert Join Code",
                icon = Icons.Filled.JoinFull
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

            CardSectionProfile(
                onClick = {
                    onEvent(ProfileEvent.SetAlertLogoutStatus(true))
                },
                title = "Logout",
                icon = Icons.Filled.Logout
            )
        }


        SimpleAlertDialog(
            show = state.isShowAlertLogout,
            message = "Are you sure, logout from this account ?",
            onDismiss = {
                onEvent(ProfileEvent.SetAlertLogoutStatus(false))
            },
            onConfirm = {
                onEvent(ProfileEvent.Logout {
                    if (it) {
                        navHostController.navigate(Screen.Login.screenRoute) {
                            popUpTo(
                                Screen.Dashboard.screenRoute
                            ) {
                                inclusive = true
                            }
                            restoreState = false
                            launchSingleTop = true
                        }

                        onEvent(ProfileEvent.SetAlertLogoutStatus(false))
                    }
                })
            })

        DialogInviteUser(
            listEdgeServer = state.listServer,
            errorSelectServer = "",
            isLoading = state.isLoadingGetInvitationCode,
            selectedServerLabel = state.selectedServerLabel,
            onInviteUser = {
                if (state.selectedServerId != null) {
                    onEvent(ProfileEvent.GetTokenInviteUser(state.selectedServerId))
                }
            },
            isOpen = state.isOpenDialogInviteUser,
            onChangeServer = {
                onEvent(ProfileEvent.OnChangeServerId(it))
            },
            onClose = {
                onEvent(ProfileEvent.SetStatusOpenDialogInviteUser(false))
            }
        )

        DialogJoinUserGroup(
            isOpen = state.isOpenDialogJoinUserGroup,
            errorText = state.errorJoinUserGroup,
            invitationCode = state.inputInvitationCode,
            onChangeInvitationCode = {
                onEvent(ProfileEvent.SetInputInvitationCode(it))
            },
            isLoading = state.isLoadingJoinUserGroup,
            onJoinUserGroup = {
                onEvent(ProfileEvent.JoinUserGroup)
            },
            onClose = {
                onEvent(ProfileEvent.SetStatusOpenDialogJoinUser(false))
            }
        )

        if (state.isOpenDialogSuccessGetInvitationCode) {
            Dialog(onDismissRequest = {
                onEvent(ProfileEvent.SetDialogSuccessGetInvitationCode(false))
            }) {
                Column(
                    modifier = Modifier
                        .shadow(
                            elevation = 4.dp,
                            spotColor = Color.Gray,
                            ambientColor = Color.Gray
                        )
                        .width(360.dp)
                        .clip(RoundedCornerShape(size = 8.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CustomInputField(
                        label = "Your invitation token",
                        enabled = false,
                        text = state.invitationCode,
                        errorText = "",
                        iconStart = false,
                        onClickIcon = {
                            clipboardManager.setPrimaryClip(
                                ClipData.newPlainText(
                                    "text",
                                    state.invitationCode
                                )
                            )
                            Toast.makeText(context, "Copied to Clipboard", Toast.LENGTH_SHORT)
                                .show()
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.CopyAll,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        onTextChanged = {}
                    )
                }
            }
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