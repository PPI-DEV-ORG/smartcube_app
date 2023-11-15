package com.ppidev.smartcube.presentation.reset_password.change_password

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.outlined.HideSource
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ppidev.smartcube.ui.Screen
import com.ppidev.smartcube.ui.components.form.CustomInputField
import com.ppidev.smartcube.ui.components.modal.DialogApp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ChangePasswordScreen(
    state: ChangePasswordState,
    onEvent: (ChangePasswordEvent) -> Unit,
    argToken: String,
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {

        TopAppBar(title = {
            Text(text = "Change Password")
        })

        Column(
            modifier = Modifier
                .padding(top = 40.dp, bottom = 40.dp)
                .padding(horizontal = 39.dp),
            horizontalAlignment = Alignment.Start,

            ) {


            Spacer(modifier = Modifier.size(100.dp))

            if (state.error.message.isNotEmpty()) {
                Text(modifier = Modifier
                    .semantics { testTagsAsResourceId = true }
                    .testTag("text_errorMessage"),
                    text = state.error.message,
                    style = TextStyle(
                        fontSize = 14.sp, color = Color.Red
                    ))
            }

            Spacer(modifier = Modifier.size(20.dp))


            CustomInputField(modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .testTag("input_password"),
                text = state.password,
                label = "New Password",
                showText = state.isShowPassword,
                iconStart = false,
                errorText = state.error.password,
                onClickIcon = {
                    onEvent(ChangePasswordEvent.ToggleShowPassword)
                },
                icon = {
                    if (state.isShowPassword) Icon(
                        imageVector = Icons.Filled.RemoveRedEye,
                        contentDescription = "hide password"
                    )
                    else Icon(
                        imageVector = Icons.Outlined.HideSource,
                        contentDescription = "show password"
                    )
                },
                enabled = !state.isLoading,
                keyboardType = KeyboardType.Password,
                onTextChanged = {
                    onEvent(ChangePasswordEvent.OnChangePassword(it))
                })


            Spacer(modifier = Modifier.size(20.dp))


            CustomInputField(modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .testTag("input_confirm_password"),
                text = state.confirmPassword,
                label = "Confirm New Password",
                showText = state.isShowConfirmPassword,
                iconStart = false,
                errorText = state.error.confirmPassword,
                onClickIcon = {
                    onEvent(ChangePasswordEvent.ToggleShowConfirmPassword)
                },
                icon = {
                    if (state.isShowConfirmPassword) Icon(
                        imageVector = Icons.Filled.RemoveRedEye,
                        contentDescription = "hide password"
                    )
                    else Icon(
                        imageVector = Icons.Outlined.HideSource,
                        contentDescription = "show password"
                    )
                },
                enabled = !state.isLoading,
                keyboardType = KeyboardType.Password,
                onTextChanged = {
                    onEvent(ChangePasswordEvent.OnChangeConfirmPassword(it))
                })

            Spacer(modifier = Modifier.size(20.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { testTagsAsResourceId = true }
                    .testTag("btn_send_reset_password"),
                onClick = {
                    onEvent(ChangePasswordEvent.HandleChangePassword(resetToken = argToken) { })
                },
                enabled = !state.isLoading
            ) {
                Text(text = "Submit")
            }

            if (state.isShowDialog) {
                DialogApp(message = "Success",
                    contentMessage = "Success change password",
                    isSuccess = true,
                    onDismiss = { /*TODO*/ },
                    onConfirm = {
                        onEvent(ChangePasswordEvent.HandleCloseDialog {
                            navHostController.popBackStack()
                            navHostController.navigate(Screen.Login.screenRoute)
                        })
                    })
            }
        }

    }
}