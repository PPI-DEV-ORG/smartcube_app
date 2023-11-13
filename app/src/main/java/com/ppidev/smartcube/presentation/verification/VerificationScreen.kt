package com.ppidev.smartcube.presentation.verification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.ppidev.smartcube.presentation.register.RegisterEvent
import com.ppidev.smartcube.presentation.register.RegisterState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificationScreen(
    state: VerificationState,
    onEvent: (event: VerificationEvent) -> Unit,
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
            onClick = { onEvent(VerificationEvent.HandleVerification) },
            enabled = state.verificationCode.length == 6,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Verify")
        }

        if (state.isVerificationSuccessful) {
            SuccessDialog(
                message = "Verification Successful!",
                onCloseDialog = {  },
                onConfirm = { }
            )
        }
    }
}

@Composable
fun SuccessDialog(
    message: String,
    onCloseDialog: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(onDismissRequest = { onCloseDialog() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = message,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        onConfirm()
                        onCloseDialog()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "OK")
                }
            }
        }
    }
}
