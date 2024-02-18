package com.ppidev.smartcube.domain.use_case.auth

import com.ppidev.smartcube.contract.data.repository.IAuthRepository
import com.ppidev.smartcube.contract.domain.use_case.auth.IRequestLinkResetPasswordUseCase
import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.Resource
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject


class RequestLinkResetPasswordUseCase @Inject constructor(
    private val authRepository: Lazy<IAuthRepository>
) : IRequestLinkResetPasswordUseCase {
    override fun invoke(email: String): Flow<Resource<String?, Any>> = flow {
        emit(Resource.Loading())
        emit(requestLinkResetPassword(email))
    }

    private suspend fun requestLinkResetPassword(email: String): Resource<String?, Any> {
        try {
            val requestLinkResetPasswordResponse = authRepository.get().resetPasswordRequest(
                email = email
            )

            if (!requestLinkResetPasswordResponse.status) {
                return Resource.Error(
                    requestLinkResetPasswordResponse.statusCode,
                    requestLinkResetPasswordResponse.message
                )
            }
            return Resource.Success(
                requestLinkResetPasswordResponse.message,
                requestLinkResetPasswordResponse.data
            )
        } catch (e: IOException) {
            return Resource.Error(
                EExceptionCode.UseCaseError.code,
                e.message ?: "Something wrong"
            )
        }
    }
}