package com.ppidev.smartcube.domain.use_case.auth

import com.ppidev.smartcube.common.EExceptionCode
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.common.ResponseApp
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
            try {
                emit(Resource.Loading())
                val loginResponse = authRepository.get().login(email, password)

                if (!loginResponse.status) {
                    emit(
                        Resource.Error(
                            loginResponse.statusCode,
                            loginResponse.message
                        )
                    )

                    return@flow
                }

                emit(Resource.Success(loginResponse))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        EExceptionCode.UseCaseError.code,
                        e.message ?: "Something wrong"
                    )
                )
            }
        }
}