package com.ppidev.smartcube.contract.domain.use_case.auth

import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.common.Response
import com.ppidev.smartcube.data.remote.dto.LoginDto
import com.ppidev.smartcube.data.remote.dto.RegisterDto
import com.ppidev.smartcube.data.remote.dto.VerificationDto
import kotlinx.coroutines.flow.Flow

interface ILoginUseCase {
    operator fun invoke(email: String, password: String): Flow<Resource<Response<LoginDto?>>>
}

interface IRegisterUseCase {
    operator fun invoke(username: String, email: String, password: String, confirmPassword: String): Flow<Resource<Response<RegisterDto?>>>
}

interface IVerificationUseCase{
    operator fun invoke(email: String,verificationCode : String ): Flow<Resource<Response<VerificationDto?>>>
}

