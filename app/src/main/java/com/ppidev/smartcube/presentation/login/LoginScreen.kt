package com.ppidev.smartcube.presentation.login

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ppidev.smartcube.ui.components.form.CustomInputField

@Composable
fun LoginScreen(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit
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
                text = state.error.message, style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Red
                )
            )
        }

        Spacer(modifier = Modifier.size(20.dp))

        CustomInputField(
            text = state.email,
            label = "Email",
            errorText = state.error.email,
            keyboardType = KeyboardType.Email,
            onTextChanged = {
                onEvent(LoginEvent.OnEmailChange(it))
            })

        Spacer(modifier = Modifier.size(20.dp))

        CustomInputField(
            text = state.password,
            label = "Password",
            showText = state.isShowPassword,
            iconStart = false,
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
                        imageVector = Icons.Outlined.HideSource,
                        contentDescription = "show password"
                    )
            },
            keyboardType = KeyboardType.Password,
            onTextChanged = {
                onEvent(LoginEvent.OnPasswordChange(it))
            })

        Spacer(modifier = Modifier.size(20.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Forgot Password",
            textAlign = TextAlign.Right
        )

        Spacer(modifier = Modifier.size(40.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onEvent(LoginEvent.HandleLogin) },
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

        Text(
            modifier = Modifier
                .clickable {
                    onEvent(LoginEvent.ToRegisterScreen)
                }
                .fillMaxWidth(),
            text = "Register",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
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
        onEvent = {}
    )
}