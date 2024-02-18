package com.ppidev.smartcube.domain.use_case.auth

import android.content.Context
import android.util.Log
import com.ppidev.smartcube.contract.data.repository.IAuthRepository
import com.ppidev.smartcube.contract.domain.use_case.auth.ILoginUseCase
import com.ppidev.smartcube.data.remote.dto.LoginDto
import com.ppidev.smartcube.presentation.login.LoginState.LoginError
import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.utils.validateEmail
import com.ppidev.smartcube.utils.validatePassword
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: Lazy<IAuthRepository>,
    private val context: Context
) : ILoginUseCase {
    override fun invoke(email: String, password: String): Flow<Resource<LoginDto?, Any>> =
        flow {
            emit(Resource.Loading())
            emit(login(email, password))
        }

    private suspend fun login(email: String, password: String): Resource<LoginDto?, Any> {
//        Log.d("CONTEK", context.getString(R.string.app_name))
        if (email.isEmpty() || password.isEmpty() || !validateEmail(email) || !validatePassword(password)) {
            val emailError = if (email.isEmpty() || !validateEmail(email)) "Email is not valid" else ""
            val passwordError = if (password.isEmpty() || !validatePassword(password)) "Password must be > 8 characters" else ""
            return Resource.Error(
                statusCode = EExceptionCode.UseCaseError.code,
                message = "Invalid email or password",
                data = LoginError(
                    email = emailError,
                    password = passwordError
                )
            )
        }

        try {
            val loginResponse = authRepository.get().login(email, password)

            if (!loginResponse.status) {
                return Resource.Error(
                    loginResponse.statusCode,
                    loginResponse.message
                )
            }

            return Resource.Success(loginResponse.message, loginResponse.data)
        } catch (e: Exception) {
            Log.d("LOGIN_SUSECASE", e.message.toString())
            return Resource.Error(
                EExceptionCode.UseCaseError.code,
                e.message ?: "Something wrong"
            )
        }
    }
}