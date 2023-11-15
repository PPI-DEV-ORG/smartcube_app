package com.ppidev.smartcube.presentation.verification

data class VerificationState(
    val error : VerificationError,
    val email: String = "",
    val verificationCode: String = "",
    val isVerificationSuccessful: Boolean = false,
    val isVerificationFailed: Boolean = false,
    val isLoading: Boolean = false,
    val isShowDialog: Boolean = false,
    val successToken: Any = ""
) {
    data class VerificationError(
        val email: String = "",
        val verificationCode: String = "",
        val message: String = ""
    )
}
