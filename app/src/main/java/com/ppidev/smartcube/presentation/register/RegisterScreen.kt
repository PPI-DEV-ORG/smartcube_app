package com.ppidev.smartcube.presentation.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.outlined.HideSource
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ppidev.smartcube.R
import com.ppidev.smartcube.ui.Screen
import com.ppidev.smartcube.ui.components.form.CustomInputField




@OptIn(ExperimentalComposeUiApi::class)
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
            modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .testTag("input_username"),
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
            modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .testTag("input_email"),
            errorTestTag= "text_error_email" ,
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
            modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .testTag("input_password"),
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
            modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .testTag("input_confirm_password"),
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
            modifier = Modifier.fillMaxWidth().semantics { testTagsAsResourceId = true }
                .testTag("btn_signup"),
            onClick = { onEvent(RegisterEvent.HandleRegister) },
            enabled = !state.isLoading
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
                        navHostController.navigate(Screen.Login.screenRoute) {
                            popUpTo(Screen.Login.screenRoute) {
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

        if (state.isShowDialog) {
            DialogRegister(
                onDismiss = {},
                onConfirm = {
                    onEvent(RegisterEvent.HandleCloseDialog{
                        navHostController.navigate(Screen.Verification.screenRoute + "/${state.email}")
                    })
                }
            )
        }
    }
}


@Composable
fun DialogRegister(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
//        properties = DialogProperties(
//            usePlatformDefaultWidth = false
//        )
    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.95f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Registration Successfully",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 20.sp
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(90.dp),
                        painter = painterResource(id = R.drawable.ic_check_circle),
                        contentDescription = "Success",
                        tint = Color.Unspecified
                    )
                }

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Please check your email to verify account",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 16.sp
                    )
                )

                Button(
                    onClick = {
                        onConfirm()
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = CircleShape
                ) {
                    Text(
                        text = "Confirm",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DialogRegisterPreview() {
    DialogRegister(
        onConfirm = {},
        onDismiss = {}
    )
}


@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        state = RegisterState(
            error = RegisterState.RegisterError()
        ),
        onEvent = {},
        navHostController = rememberNavController()
    )
}