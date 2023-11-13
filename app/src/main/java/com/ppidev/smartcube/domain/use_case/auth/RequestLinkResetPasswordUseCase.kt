package com.ppidev.smartcube.domain.use_case.auth

import android.util.Log
import com.ppidev.smartcube.common.EExceptionCode
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.contract.data.repository.IAuthRepository
import com.ppidev.smartcube.contract.domain.use_case.auth.IRequestLinkResetPasswordUseCase
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class RequestLinkResetPasswordUseCase @Inject constructor(
    private val authRepository: Lazy<IAuthRepository>
) : IRequestLinkResetPasswordUseCase {
    override fun invoke(email: String): Flow<Resource<ResponseApp<String?>>> = flow {
        try {
            emit(Resource.Loading())

            val requestLinkResetPasswordResponse = authRepository.get().resetPasswordRequest(
                email = email
            )

            if (!requestLinkResetPasswordResponse.status) {
                emit(
                    Resource.Error(
                        requestLinkResetPasswordResponse.statusCode,
                        requestLinkResetPasswordResponse.message
                    )
                )
                return@flow
            }

            emit(Resource.Success(requestLinkResetPasswordResponse))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    EExceptionCode.UseCaseError.code,
                    e.message ?: "Something wrong"
                )
            )
        }
    }
}