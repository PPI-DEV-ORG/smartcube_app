package com.ppidev.smartcube.domain.use_case.auth

import android.util.Log
import com.ppidev.smartcube.common.EExceptionCode
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.contract.data.repository.IAuthRepository
import com.ppidev.smartcube.contract.domain.use_case.auth.IChangePasswordUseCase
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val authRepository: Lazy<IAuthRepository>
): IChangePasswordUseCase {
    override fun invoke(
        resetToken: String,
        newPassword: String,
        newConfirmationPassword: String
    ): Flow<Resource<ResponseApp<Boolean?>>> = flow {
        try {
            emit(Resource.Loading())
            val response = authRepository.get().changePassword(resetToken, newPassword, newConfirmationPassword)

            if (!response.status) {
                emit(
                    Resource.Error(
                        response.statusCode,
                        response.message
                    )
                )

                return@flow
            }

            emit(Resource.Success(response))
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