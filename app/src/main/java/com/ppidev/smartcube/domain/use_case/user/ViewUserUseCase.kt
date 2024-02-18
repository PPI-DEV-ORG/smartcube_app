package com.ppidev.smartcube.domain.use_case.user

import com.ppidev.smartcube.contract.data.repository.IUserRepository
import com.ppidev.smartcube.contract.domain.use_case.user.IViewUserUseCase
import com.ppidev.smartcube.data.remote.dto.UserDto
import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.utils.ResponseApp
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ViewUserUseCase @Inject constructor(private val userRepository: Lazy<IUserRepository>) :
    IViewUserUseCase {
    override suspend fun invoke(): Flow<Resource<UserDto?, Any>> = flow {
        emit(Resource.Loading())
        emit(getProfile())
    }

    private suspend fun getProfile(): Resource<UserDto?, Any> {
        return try {
            val response =
                userRepository.get().getUserProfile()

            if (!response.status) {
                return Resource.Error<ResponseApp<UserDto?>>(
                    response.statusCode, response.message
                )
            }

            val result: ResponseApp<UserDto?> = ResponseApp(
                status = response.status,
                statusCode = response.statusCode,
                message = response.message,
                data = response.data
            )

            Resource.Success(result.message, result.data)
        } catch (e: Exception) {
            Resource.Error<ResponseApp<UserDto?>>(
                EExceptionCode.HTTPException.ordinal, e.message ?: "Something wrong"
            )
        }
    }
}