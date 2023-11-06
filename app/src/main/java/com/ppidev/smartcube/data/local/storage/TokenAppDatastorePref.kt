package com.ppidev.smartcube.data.local.storage

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ppidev.smartcube.contract.data.local.storage.ITokenAppDataStorePref
import com.ppidev.smartcube.data.local.entity.TokenAppEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

const val TOKEN_APP_PREFERENCE = "app-token-ds"
//val Context.appTokenDataStore by preferencesDataStore(name = TOKEN_APP_PREFERENCE)

class TokenAppDatastorePref @Inject constructor(private val dataStore: DataStore<Preferences>) :
    ITokenAppDataStorePref<TokenAppEntity> {

    override fun get(): Flow<TokenAppEntity> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map {
        Log.d("TOKEN_KEY", it[ACCESS_TOKEN_KEY].toString())
        TokenAppEntity(
            accessToken = it[ACCESS_TOKEN_KEY] ?: "",
            fcmToken = it[FCM_TOKEN_KEY] ?: ""
        )
    }

    override suspend fun getAccessToken(): String? {
        return dataStore.data.firstOrNull()?.get(ACCESS_TOKEN_KEY)
    }

    override suspend fun getFcmToken(): String? {
        return dataStore.data.firstOrNull()?.get(FCM_TOKEN_KEY)
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

    override suspend fun updateAccessToken(newToken: String): Boolean {
        return try {
            dataStore.edit {
                it[ACCESS_TOKEN_KEY] = newToken
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updateFcmToken(newToken: String): Boolean {
        return try {
            dataStore.edit {
                it[FCM_TOKEN_KEY] = newToken
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun removeFcmToken(): Boolean {
        return try {
            dataStore.edit {
                it[FCM_TOKEN_KEY] = ""
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun removeAccessToken(): Boolean {
        return try {
            dataStore.edit {
                it[ACCESS_TOKEN_KEY] = ""
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("accessToken")
        private val FCM_TOKEN_KEY = stringPreferencesKey("fcmToken")
    }
}