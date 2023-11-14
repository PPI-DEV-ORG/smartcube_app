package com.ppidev.smartcube.domain.use_case.auth

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
) : IChangePasswordUseCase {
    override fun invoke(
        resetToken: String, newPassword: String, newConfirmationPassword: String
    ): Flow<Resource<ResponseApp<Boolean?>>> = flow {
        emit(Resource.Loading())
        emit(changePassword(resetToken, newPassword, newConfirmationPassword))
    }

    private suspend fun changePassword(
        resetToken: String, newPassword: String, newConfirmationPassword: String
    ): Resource<ResponseApp<Boolean?>> {
        try {
            val response = authRepository.get()
                .changePassword(resetToken, newPassword, newConfirmationPassword)

            if (!response.status) {
                return Resource.Error(
                    response.statusCode, response.message
                )
            }

            return Resource.Success(response)
        } catch (e: Exception) {
            return Resource.Error(
                EExceptionCode.UseCaseError.code, e.message ?: "Something wrong"
            )
        }
    }
}