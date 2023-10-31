package com.ppidev.smartcube.domain.use_case.auth

import com.ppidev.smartcube.common.EExceptionCode
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.common.Response
import com.ppidev.smartcube.contract.data.repository.IAuthRepository
import com.ppidev.smartcube.contract.domain.use_case.auth.ILoginUseCase
import com.ppidev.smartcube.data.remote.dto.LoginDto
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: Lazy<IAuthRepository>
) : ILoginUseCase {
    override fun invoke(email: String, password: String): Flow<Resource<Response<LoginDto?>>> =
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
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        EExceptionCode.HTTPException.ordinal,
                        "Couldn't reach server. Check your internxet connection."
                    )
                )
            }
        }
}