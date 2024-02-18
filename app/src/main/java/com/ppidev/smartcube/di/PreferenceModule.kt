package com.ppidev.smartcube.di

import com.ppidev.smartcube.contract.data.local.storage.ITokenAppDataStorePref
import com.ppidev.smartcube.data.local.entity.TokenAppEntity
import com.ppidev.smartcube.data.local.storage.TokenAppDatastorePref
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferenceModule {
    @Binds
    @Singleton
    abstract fun bindTokenPreference(
        tokenAppDataStore: TokenAppDatastorePref
    ): ITokenAppDataStorePref<TokenAppEntity>

//    @Binds
//    @Singleton
//    abstract fun bindAppSettingPreference(impl: AppSettingDatastore): IAppSettingDataStore<AppSettingEntity>
}
