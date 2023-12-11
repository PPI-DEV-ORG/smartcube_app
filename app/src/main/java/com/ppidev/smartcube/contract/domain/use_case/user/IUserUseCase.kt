package com.ppidev.smartcube.contract.domain.use_case.user

import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.common.ResponseApp
import kotlinx.coroutines.flow.Flow


interface IUpdateFcmTokenUseCase {
    suspend fun invoke(fcmToken: String): Flow<Resource<ResponseApp<Any?>>>
}