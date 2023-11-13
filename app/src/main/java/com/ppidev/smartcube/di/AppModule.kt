package com.ppidev.smartcube.di

import androidx.datastore.core.DataStore
import com.ppidev.smartcube.BuildConfig
import com.ppidev.smartcube.contract.data.local.storage.ITokenAppDataStorePref
import com.ppidev.smartcube.data.local.entity.TokenAppEntity
import com.ppidev.smartcube.data.remote.api.AuthInterceptor
import com.ppidev.smartcube.data.remote.api.SmartCubeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.prefs.Preferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSmartCubeApi(tokenDataStorePref: ITokenAppDataStorePref<TokenAppEntity>): SmartCubeApi {
        val interceptor = AuthInterceptor(tokenDataStorePref)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(SmartCubeApi::class.java)
    }
}
