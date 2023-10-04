package com.ppidev.smartcube.data.local.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ppidev.smartcube.contract.data.local.storage.IDatastoreManager
import com.ppidev.smartcube.data.local.dataclass.TokenAppEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_FILE_APP_SETTING = "app-token-ds"
val Context.appTokenDataStore by preferencesDataStore(name = DATASTORE_FILE_APP_SETTING)

class TokenAppDatastorePreferenceManager private constructor(private val dataStore: DataStore<Preferences>) :
    IDatastoreManager<TokenAppEntity> {
    override suspend fun get(): Flow<TokenAppEntity> = dataStore.data.map {
        TokenAppEntity(
            accessToken = it[ACCESS_TOKEN_KEY] ?: "",
            fcmToken = it[FCM_TOKEN_KEY] ?: ""
        )
    }

    override suspend fun reset(): Boolean {
        return try {
            dataStore.edit {
                it.clear()
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun set(data: TokenAppEntity): Boolean {
        return try {
            dataStore.edit {
                it[ACCESS_TOKEN_KEY] = data.accessToken
                it[FCM_TOKEN_KEY] = data.fcmToken
            }
            true
        } catch (e: Exception) {
            return false
        }
    }

    suspend fun updateAccessToken(accessToken: String) {
        dataStore.edit {
            it[ACCESS_TOKEN_KEY] = accessToken
        }
    }

    suspend fun updateFcmToken(fcmToken: String) {
        dataStore.edit {
            it[FCM_TOKEN_KEY] = fcmToken
        }
    }

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("accessToken")
        private val FCM_TOKEN_KEY = stringPreferencesKey("fcmToken")

        @Volatile
        private var INSTANCE: TokenAppDatastorePreferenceManager? = null

        fun getInstance(dataStore: DataStore<Preferences>) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: TokenAppDatastorePreferenceManager(dataStore).also {
                INSTANCE = it
            }
        }
    }
}