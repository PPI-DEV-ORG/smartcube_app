package com.ppidev.smartcube.domain.use_case.auth

import android.util.Log
import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.utils.ResponseApp
import com.ppidev.smartcube.contract.data.repository.IAuthRepository
import com.ppidev.smartcube.contract.domain.use_case.auth.ILoginUseCase
import com.ppidev.smartcube.data.remote.dto.LoginDto
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: Lazy<IAuthRepository>
) : ILoginUseCase {
    override fun invoke(email: String, password: String): Flow<Resource<ResponseApp<LoginDto?>>> =
        flow {
            emit(Resource.Loading())
            emit(login(email, password))
        }

    private suspend fun login(email: String, password: String): Resource<ResponseApp<LoginDto?>> {
        try {
            val loginResponse = authRepository.get().login(email, password)
            if (!loginResponse.status) {
                return Resource.Error(
                    loginResponse.statusCode,
                    loginResponse.message
                )
            }
            return Resource.Success(loginResponse)
        } catch (e: Exception) {
            Log.d("LOGIN_SUSECASE", e.message.toString())
            return Resource.Error(
                EExceptionCode.UseCaseError.code,
                e.message ?: "Something wrong"
            )
        }
    }
}