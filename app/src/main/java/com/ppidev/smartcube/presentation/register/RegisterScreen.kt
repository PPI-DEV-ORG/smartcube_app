package com.ppidev.smartcube.presentation.register

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ppidev.smartcube.ui.Screen
import com.ppidev.smartcube.ui.components.form.CustomInputField

@Composable
fun RegisterScreen(
    state: RegisterState,
    onEvent: (event: RegisterEvent) -> Unit,
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
            text = "Sign Up", style = TextStyle(
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
            text = state.username,
            label = "Username",
            errorText = state.error.username,
            keyboardType = KeyboardType.Text,
            enabled = !state.isLoading,
            onTextChanged = {
                onEvent(RegisterEvent.OnUsernameChange(it))
            })


        Spacer(modifier = Modifier.size(20.dp))

        CustomInputField(
            text = state.email,
            label = "Email",
            errorText = state.error.email,
            keyboardType = KeyboardType.Email,
            enabled = !state.isLoading,
            onTextChanged = {
                onEvent(RegisterEvent.OnEmailChange(it))
            })

        Spacer(modifier = Modifier.size(20.dp))

        CustomInputField(
            text = state.password,
            label = "Password",
            showText = state.isShowPassword,
            iconStart = false,
            errorText = state.error.password,
            onClickIcon = {
                onEvent(RegisterEvent.ToggleShowPassword)
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
            enabled = !state.isLoading,
            keyboardType = KeyboardType.Password,
            onTextChanged = {
                onEvent(RegisterEvent.OnPasswordChange(it))
            })

        Spacer(modifier = Modifier.size(20.dp))

        CustomInputField(
            text = state.confirmPassword,
            label = "Confirmation Password",
            showText = state.isShowConfirmPassword,
            iconStart = false,
            errorText = state.error.confirmPassword,
            onClickIcon = {
                onEvent(RegisterEvent.ToggleShowConfirmPassword)
            },
            icon = {
                if (state.isShowConfirmPassword)
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
            enabled = !state.isLoading,
            keyboardType = KeyboardType.Password,
            onTextChanged = {
                onEvent(RegisterEvent.OnConfirmPasswordChange(it))
            })


        Spacer(modifier = Modifier.size(40.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onEvent(RegisterEvent.HandleRegister) },
            enabled = true
        ) {
            Text(text = "Sign Up")
        }

        Spacer(modifier = Modifier.size(100.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Have an account ?",
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(6.dp))

        Text(
            modifier = Modifier
                .clickable {
                    onEvent(RegisterEvent.ToLoginScreen {
                        navHostController.navigate(Screen.Login.screenRoute){
                            popUpTo(Screen.Login.screenRoute){
                                inclusive = true
                            }
                        }
                    })
                }
                .fillMaxWidth(),
            text = "Login",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
    }
}