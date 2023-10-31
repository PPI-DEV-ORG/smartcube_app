package com.ppidev.smartcube.contract.data.repository

import com.ppidev.smartcube.data.local.entity.TokenAppEntity
import kotlinx.coroutines.flow.Flow

interface ITokenAppRepository {

    fun getTokenApp(): Flow<TokenAppEntity>
    fun getAccessToken(): Flow<String>
    fun getFcmToken(): Flow<String>

    suspend fun updateFcmToken(newToken: String): Boolean

    suspend fun updateAccessToken(newToken: String): Boolean

    suspend fun removeFcmToken(): Boolean

    suspend fun removeAccessToken(): Boolean
}