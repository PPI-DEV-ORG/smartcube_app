package com.ppidev.smartcube.domain.use_case.user

import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.utils.ResponseApp
import com.ppidev.smartcube.contract.data.repository.IUserRepository
import com.ppidev.smartcube.contract.domain.use_case.user.IUpdateFcmTokenUseCase
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateFcmTokenUseCase @Inject constructor(
    private val userRepository: Lazy<IUserRepository>
) : IUpdateFcmTokenUseCase {
    override suspend fun invoke(fcmToken: String): Flow<Resource<Any?, Any>> = flow {
        emit(Resource.Loading())
        emit(updateFcmToken(fcmToken))
    }

    private suspend fun updateFcmToken(fcmToken: String): Resource<Any?, Any> {
        return try {
            val response =
                userRepository.get().updateFcmToken(fcmToken = fcmToken)

            if (!response.status) {
                return Resource.Error<ResponseApp<Any?>>(
                    response.statusCode, response.message
                )
            }

            val result: ResponseApp<Any?> = ResponseApp(
                status = response.status,
                statusCode = response.statusCode,
                message = response.message,
                data = response.data
            )

            Resource.Success(result.message, result.data)
        } catch (e: Exception) {
            Resource.Error<ResponseApp<Any?>>(
                EExceptionCode.HTTPException.ordinal, e.message ?: "Something wrong"
            )
        }
    }
}