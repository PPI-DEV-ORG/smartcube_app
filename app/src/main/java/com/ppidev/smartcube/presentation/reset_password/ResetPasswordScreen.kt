package com.ppidev.smartcube.presentation.reset_password

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ppidev.smartcube.ui.components.form.CustomInputField
import com.ppidev.smartcube.ui.components.modal.DialogApp

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(
    state: ResetPasswordState,
    onEvent: (ResetPasswordEvent) -> Unit,
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {

        TopAppBar(title = {
            Text(text = "Forgot Password")
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
                .testTag("input_email"),
                text = state.email,
                label = "Email",
                errorText = state.error.email,
                keyboardType = KeyboardType.Email,
                errorTestTag = "text_errorEmail",
                enabled = !state.isLoading,
                onTextChanged = {
                    onEvent(ResetPasswordEvent.OnEmailChange(it))
                })

            Spacer(modifier = Modifier.size(20.dp))

            Button(modifier = Modifier
                .fillMaxWidth()
                .semantics { testTagsAsResourceId = true }
                .testTag("btn_send_reset_password"), onClick = {
                onEvent(ResetPasswordEvent.HandleRequestResetLinkPassword {

                })
            }, enabled = !state.isLoading
            ) {
                Text(text = "Submit")
            }

            if (state.isShowDialog) {
                DialogApp(
                    message = "Success",
                    contentMessage = "Please check your email",
                    isSuccess = true,
                    onDismiss = { /*TODO*/ },
                    onConfirm = {
                        onEvent(ResetPasswordEvent.HandleCloseDialog)
                    })
            }
        }

    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ResetPasswordScreenPreview() {
    ResetPasswordScreen(
        state = ResetPasswordState(
            error = ResetPasswordState.RequestLinkResetPasswordError()
        ),
        onEvent = {},
        navHostController = rememberNavController()
    )
}