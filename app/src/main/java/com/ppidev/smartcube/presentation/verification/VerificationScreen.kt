package com.ppidev.smartcube.presentation.verification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ppidev.smartcube.presentation.reset_password.ResetPasswordEvent
import com.ppidev.smartcube.ui.Screen
import com.ppidev.smartcube.ui.components.modal.DialogApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificationScreen(
    state: VerificationState,
    onEvent: (event: VerificationEvent) -> Unit,
    email: String,
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Verification",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = state.verificationCode,
            onValueChange = { onEvent(VerificationEvent.OnVerificationCodeChange(it)) },
            label = { Text(text = "Masukan Otp Anda") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                onEvent(VerificationEvent.HandleVerification(email) {
                    navHostController.popBackStack()
                    navHostController.navigate(Screen.Login.screenRoute)
                })
            },
            enabled = state.verificationCode.length == 6,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Verify")
        }

        if (state.isVerificationSuccessful) {
            DialogApp(
                message = "Success",
                contentMessage = "Verification Successful!",
                isSuccess = true,
                onDismiss = { /*TODO*/ },
                onConfirm = {
                    onEvent(VerificationEvent.HandleCloseDialog)
                })
        }
    }
}