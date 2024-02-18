package com.ppidev.smartcube.contract.domain.use_case.user

import com.ppidev.smartcube.data.remote.dto.UserDto
import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.utils.ResponseApp
import kotlinx.coroutines.flow.Flow

interface IUpdateFcmTokenUseCase {
    suspend fun invoke(fcmToken: String): Flow<Resource<Any?, Any>>
}

interface IViewUserUseCase {
    suspend fun invoke(): Flow<Resource<UserDto?, Any>>
}