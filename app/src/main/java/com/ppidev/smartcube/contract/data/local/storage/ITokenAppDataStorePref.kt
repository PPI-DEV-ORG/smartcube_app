package com.ppidev.smartcube.contract.data.local.storage

import kotlinx.coroutines.flow.Flow

interface ITokenAppDataStorePref<T> {
    fun get(): Flow<T>
    suspend fun getAccessToken(): String?
    suspend fun getFcmToken(): String?
    suspend fun set(data: T): Boolean
    suspend fun reset(): Boolean
    suspend fun updateAccessToken(newToken: String): Boolean
    suspend fun updateFcmToken(newToken: String): Boolean
    suspend fun removeFcmToken(): Boolean
    suspend fun removeAccessToken(): Boolean
}