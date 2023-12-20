package com.ppidev.smartcube.presentation.login

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.outlined.HideSource
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ppidev.smartcube.BuildConfig
import com.ppidev.smartcube.R
import com.ppidev.smartcube.ui.Screen
import com.ppidev.smartcube.ui.components.form.CustomInputField

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(top = 40.dp, bottom = 40.dp)
            .padding(horizontal = 39.dp),
        horizontalAlignment = Alignment.Start,

        ) {

        Spacer(modifier = Modifier.size(100.dp))

        Text(
            text = "Sign In", style = TextStyle(
                fontSize = 28.sp,
                textAlign = TextAlign.Left
            )
        )

        Spacer(modifier = Modifier.size(82.dp))

        if (state.error.message.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .semantics { testTagsAsResourceId = true }
                    .testTag("text_errorMessage"),
                text = state.error.message, style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Red
                )
            )
        }

        Spacer(modifier = Modifier.size(20.dp))

        CustomInputField(
            modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .testTag("input_email"),
            placeholder = "Enter your email",
            text = state.email,
            label = "Email",
            errorText = state.error.email,
            keyboardType = KeyboardType.Email,
            errorTestTag = "text_errorEmail",
            enabled = !state.isLoginLoading,
            onTextChanged = {
                onEvent(LoginEvent.OnEmailChange(it))
            })

        Spacer(modifier = Modifier.size(20.dp))

        CustomInputField(
            modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .testTag("input_password"),
            text = state.password,
            placeholder = "Enter your password",
            label = "Password",
            showText = state.isShowPassword,
            iconStart = false,
            enabled = !state.isLoginLoading,
            errorText = state.error.password,
            onClickIcon = {
                onEvent(LoginEvent.ToggleShowPassword)
            },
            icon = {
                if (state.isShowPassword)
                    Icon(
                        imageVector = Icons.Filled.RemoveRedEye,
                        contentDescription = "hide password"
                    )
                else
                    Icon(
                        painter = painterResource(id = R.drawable.ic_eye_close),
                        contentDescription = "show password"
                    )
            },
            keyboardType = KeyboardType.Password,
            onTextChanged = {
                onEvent(LoginEvent.OnPasswordChange(it))
            })

        Spacer(modifier = Modifier.size(20.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            ClickableText(
                text = AnnotatedString("Forgot password ?"),
                onClick = {
                    onEvent(LoginEvent.ToResetPasswordScreen {
                        navHostController.navigate(Screen.ResetPassword.screenRoute)
                    })
                }, style = TextStyle(
                    textAlign = TextAlign.Right,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }


        Spacer(modifier = Modifier.size(40.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .semantics { testTagsAsResourceId = true }
                .testTag("btn_login"),
            onClick = {
                Log.d("LOGIN_CLICK", BuildConfig.API_URL.toString())
                onEvent(LoginEvent.HandleLogin {
                    navHostController.popBackStack()
                    navHostController.navigate(Screen.Dashboard.screenRoute)
                })
            },
            enabled = !state.isLoginLoading
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.size(100.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Don't have account ?",
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(6.dp))

        ClickableText(
            modifier = Modifier
                .fillMaxWidth()
                .semantics { testTagsAsResourceId = true }
                .testTag("to_register_screen"),
            onClick = {
                onEvent(LoginEvent.ToRegisterScreen {
                    navHostController.navigate(Screen.Register.screenRoute)
                })
            },
            text = AnnotatedString("Register"),
            style = TextStyle(
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        state = LoginState(
            "", ""
        ),
        onEvent = {},
        navHostController = rememberNavController()
    )
}