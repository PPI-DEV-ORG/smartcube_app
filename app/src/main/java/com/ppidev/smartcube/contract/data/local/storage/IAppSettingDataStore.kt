package com.ppidev.smartcube.contract.data.local.storage

import kotlinx.coroutines.flow.Flow

interface IAppSettingDataStore<T> {
    suspend fun get(): Flow<T>
    suspend fun set(data: T): Boolean
    suspend fun reset(): Boolean
}