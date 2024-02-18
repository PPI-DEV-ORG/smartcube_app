package com.ppidev.smartcube.domain.use_case.auth

import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.utils.ResponseApp
import com.ppidev.smartcube.contract.data.repository.IAuthRepository
import com.ppidev.smartcube.contract.domain.use_case.auth.IRegisterUseCase
import com.ppidev.smartcube.data.remote.dto.RegisterDto
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: Lazy<IAuthRepository>
) : IRegisterUseCase {
    override fun invoke(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
        fcmRegistrationToken: String
    ): Flow<Resource<ResponseApp<RegisterDto?>>> = flow {
        emit(Resource.Loading())
        emit(register(username, email, password, confirmPassword, fcmRegistrationToken))
    }

    private suspend fun register(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
        fcmRegistrationToken: String
    ): Resource<ResponseApp<RegisterDto?>> {
        try {
            val registerResponse = authRepository.get().register(
                email = email,
                username = username,
                password = password,
                confirmPassword = confirmPassword,
                fcmRegistrationToken = fcmRegistrationToken
            )

            if (!registerResponse.status) {
                return Resource.Error(
                    registerResponse.statusCode,
                    registerResponse.message
                )
            }
            return Resource.Success(registerResponse)
        } catch (e: Exception) {
            return Resource.Error(
                EExceptionCode.UseCaseError.code,
                e.message ?: "Something wrong"
            )
        }
    }
}