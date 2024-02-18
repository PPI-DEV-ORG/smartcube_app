package com.ppidev.smartcube.di

import android.app.Application
import android.content.Context
import com.ppidev.smartcube.BuildConfig
import com.ppidev.smartcube.contract.data.local.storage.ITokenAppDataStorePref
import com.ppidev.smartcube.data.local.entity.TokenAppEntity
import com.ppidev.smartcube.data.remote.api.AuthInterceptor
import com.ppidev.smartcube.data.remote.api.SmartCubeApi
import com.ppidev.smartcube.data.remote.api.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSmartCubeApi(tokenDataStorePref: ITokenAppDataStorePref<TokenAppEntity>): SmartCubeApi {
        val interceptor = AuthInterceptor(tokenDataStorePref)
        val loggingInterceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            // development build
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            // production build
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
            .callTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SmartCubeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        val okHttpClient = OkHttpClient.Builder()
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.WEATHER_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(WeatherApi::class.java)
    }

}

@Module
@InstallIn(SingletonComponent::class)
object ContextModule {
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
}