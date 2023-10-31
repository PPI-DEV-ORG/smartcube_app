package com.ppidev.smartcube.data.repository

import com.ppidev.smartcube.contract.data.local.storage.ITokenAppDataStorePref
import com.ppidev.smartcube.contract.data.repository.ITokenAppRepository
import com.ppidev.smartcube.data.local.entity.TokenAppEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenAppRepositoryImpl @Inject constructor(
    private val tokenAppDataStore: ITokenAppDataStorePref<TokenAppEntity>
) : ITokenAppRepository {
    override fun getTokenApp(): Flow<TokenAppEntity> {
        return tokenAppDataStore.get()
    }

    override fun getAccessToken(): Flow<String> {
        return tokenAppDataStore.get().map { it.accessToken }
    }

    override fun getFcmToken(): Flow<String> {
        return tokenAppDataStore.get().map { it.fcmToken }
    }

    override suspend fun updateFcmToken(newToken: String): Boolean {
        return tokenAppDataStore.updateFcmToken(newToken)
    }

    override suspend fun updateAccessToken(newToken: String): Boolean {
        return tokenAppDataStore.updateAccessToken(newToken)
    }

    override suspend fun removeFcmToken(): Boolean {
        return tokenAppDataStore.removeFcmToken()
    }

    override suspend fun removeAccessToken(): Boolean {
        return tokenAppDataStore.removeAccessToken()
    }
}