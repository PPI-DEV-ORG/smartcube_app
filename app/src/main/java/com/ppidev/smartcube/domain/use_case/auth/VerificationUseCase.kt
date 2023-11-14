package com.ppidev.smartcube.domain.use_case.auth

import com.ppidev.smartcube.common.EExceptionCode
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.contract.data.repository.IAuthRepository
import com.ppidev.smartcube.contract.domain.use_case.auth.IVerificationUseCase
import com.ppidev.smartcube.data.remote.dto.VerificationDto
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class VerificationUseCase @Inject constructor(
    private val authRepository: Lazy<IAuthRepository>
) : IVerificationUseCase {
    override fun invoke(
        email: String,
        verificationCode: String
    ): Flow<Resource<ResponseApp<VerificationDto?>>> = flow {
        emit(Resource.Loading())
        emit(verificationOtp(email, verificationCode))
    }

    private suspend fun verificationOtp(
        email: String,
        verificationCode: String
    ): Resource<ResponseApp<VerificationDto?>> {
        try {
            val verificationResponse = authRepository.get().verification(
                email = email,
                verificationCode = verificationCode
            )

            if (!verificationResponse.status) {
                return Resource.Error(
                    verificationResponse.statusCode,
                    verificationResponse.message
                )

            }

            return Resource.Success(verificationResponse)
        } catch (e: Exception) {
            return Resource.Error(
                EExceptionCode.UseCaseError.code,
                e.message ?: "Something wrong"
            )
        }
    }
}
