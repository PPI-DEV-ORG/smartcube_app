package com.ppidev.smartcube.domain.use_case.auth

import com.ppidev.smartcube.contract.data.repository.IAuthRepository
import com.ppidev.smartcube.contract.domain.use_case.auth.IChangePasswordUseCase
import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.Resource
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val authRepository: Lazy<IAuthRepository>
) : IChangePasswordUseCase {

    override fun invoke(
        resetToken: String, newPassword: String, newConfirmationPassword: String
    ): Flow<Resource<Boolean?, Any>> = flow {
        emit(Resource.Loading())
        emit(changePassword(resetToken, newPassword, newConfirmationPassword))
    }

    private suspend fun changePassword(
        resetToken: String, newPassword: String, newConfirmationPassword: String
    ): Resource<Boolean?, Any> {
        try {
            val response = authRepository.get()
                .changePassword(resetToken, newPassword, newConfirmationPassword)

            if (!response.status) {
                return Resource.Error(
                    response.statusCode, response.message
                )
            }

            return Resource.Success(response.message, response.data)
        } catch (e: Exception) {
            return Resource.Error(
                EExceptionCode.UseCaseError.code, e.message ?: "Something wrong"
            )
        }
    }
}