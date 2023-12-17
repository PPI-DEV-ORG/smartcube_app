package com.ppidev.smartcube.domain.use_case.user

import com.ppidev.smartcube.common.EExceptionCode
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.contract.data.repository.IUserRepository
import com.ppidev.smartcube.contract.domain.use_case.user.IUserProfileUseCase
import com.ppidev.smartcube.data.remote.dto.UserDto
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserProfileUseCase @Inject constructor(private val userRepository: Lazy<IUserRepository>): IUserProfileUseCase{
    override suspend fun invoke(): Flow<Resource<ResponseApp<UserDto?>>> = flow {
        emit(Resource.Loading())
        emit(getProfile())
    }

    private suspend fun getProfile(): Resource<ResponseApp<UserDto?>> {
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

            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error<ResponseApp<UserDto?>>(
                EExceptionCode.HTTPException.ordinal, e.message ?: "Something wrong"
            )
        }
    }
}