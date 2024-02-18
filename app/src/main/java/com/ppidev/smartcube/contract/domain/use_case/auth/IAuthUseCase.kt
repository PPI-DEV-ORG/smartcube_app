package com.ppidev.smartcube.contract.domain.use_case.auth

import com.ppidev.smartcube.data.remote.dto.LoginDto
import com.ppidev.smartcube.data.remote.dto.RegisterDto
import com.ppidev.smartcube.data.remote.dto.VerificationDto
import com.ppidev.smartcube.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ILoginUseCase {
    operator fun invoke(email: String, password: String): Flow<Resource<LoginDto?, Any>>
}

interface IRegisterUseCase {
    operator fun invoke(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
        fcmRegistrationToken: String
    ): Flow<Resource<RegisterDto?, Any>>
}

interface IVerificationUseCase {
    operator fun invoke(
        email: String,
        verificationCode: String
    ): Flow<Resource<VerificationDto?, Any>>
}

interface IRequestLinkResetPasswordUseCase {
    operator fun invoke(email: String): Flow<Resource<String?, Any>>
}

interface IChangePasswordUseCase {
    operator fun invoke(
        resetToken: String,
        newPassword: String,
        newConfirmationPassword: String
    ): Flow<Resource<Boolean?, Any>>
}
