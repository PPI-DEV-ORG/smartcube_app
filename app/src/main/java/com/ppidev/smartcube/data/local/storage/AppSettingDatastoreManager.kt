package com.ppidev.smartcube.data.local.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.ppidev.smartcube.contract.data.local.storage.IDatastoreManager
import com.ppidev.smartcube.data.local.dataclass.AppSettingModel
import com.ppidev.smartcube.data.local.dataclass.ELanguage
import com.ppidev.smartcube.data.local.dataclass.ETheme
import com.ppidev.smartcube.data.local.dataclass.ETypeNotification
import com.ppidev.smartcube.data.local.serializer.AppSettingSerializer
import kotlinx.coroutines.flow.Flow

private const val DATASTORE_FILE_APP_SETTING = "app-settings-ds.json"
val Context.appSettingDataStore by dataStore(DATASTORE_FILE_APP_SETTING, AppSettingSerializer)

class AppSettingDatastoreManager private constructor(private val dataStore: DataStore<AppSettingModel>) :
    IDatastoreManager<AppSettingModel> {
    override suspend fun get(): Flow<AppSettingModel> {
        return dataStore.data
    }

    override suspend fun reset(): Boolean {
        return try {
            dataStore.updateData {
                it.copy(
                    permitNotification = true,
                    language = ELanguage.ENGLISH,
                    theme = ETheme.LIGHT,
                    typeNotification = ETypeNotification.SOUND_VIBRATE
                )
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun set(data: AppSettingModel): Boolean {
        return try {
            dataStore.updateData {
                it.copy(
                    permitNotification = data.permitNotification,
                    typeNotification = data.typeNotification,
                    theme = data.theme,
                    language = data.language
                )
            }
            true
        } catch (e: Exception) {
            return false
        }
    }

    suspend fun updatePermitNotification(status: Boolean) {
        dataStore.updateData {
            it.copy(permitNotification = status)
        }
    }

    suspend fun updateTypeNotification(typeNotification: ETypeNotification) {
        dataStore.updateData {
            it.copy(typeNotification = typeNotification)
        }
    }

    suspend fun updateTheme(theme: ETheme) {
        dataStore.updateData {
            it.copy(theme = theme)
        }
    }

    suspend fun updateLanguage(language: ELanguage) {
        dataStore.updateData {
            it.copy(language = language)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppSettingDatastoreManager? = null

        fun getInstance(dataStore: DataStore<AppSettingModel>) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: AppSettingDatastoreManager(dataStore).also {
                INSTANCE = it
            }
        }
    }
}