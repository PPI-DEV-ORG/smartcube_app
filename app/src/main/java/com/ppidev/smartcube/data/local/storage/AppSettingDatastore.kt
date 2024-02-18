package com.ppidev.smartcube.data.local.storage

import androidx.datastore.core.DataStore
import com.ppidev.smartcube.contract.data.local.storage.IAppSettingDataStore
import com.ppidev.smartcube.data.local.entity.AppSettingEntity
import com.ppidev.smartcube.data.local.entity.ELanguage
import com.ppidev.smartcube.data.local.entity.ETheme
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppSettingDatastore @Inject constructor(private val dataStore: DataStore<AppSettingEntity>) :
    IAppSettingDataStore<AppSettingEntity> {
    override suspend fun get(): Flow<AppSettingEntity> {
        return dataStore.data
    }

    override suspend fun reset(): Boolean {
        return try {
            dataStore.updateData {
                it.copy(
                    language = ELanguage.ENGLISH,
                    theme = ETheme.LIGHT,
                )
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun set(data: AppSettingEntity): Boolean {
        return try {
            dataStore.updateData {
                it.copy(
                    theme = data.theme,
                    language = data.language
                )
            }
            true
        } catch (e: Exception) {
            return false
        }
    }

//    companion object {
//        @Volatile
//        private var INSTANCE: AppSettingDatastoreManager? = null
//
//        fun getInstance(dataStore: DataStore<AppSettingModel>) = INSTANCE ?: synchronized(this) {
//            INSTANCE ?: AppSettingDatastoreManager(dataStore).also {
//                INSTANCE = it
//            }
//        }
//    }
}