package com.ppidev.smartcube.di

import com.ppidev.smartcube.BuildConfig
import com.ppidev.smartcube.contract.data.local.storage.ITokenAppDataStorePref
import com.ppidev.smartcube.data.local.entity.TokenAppEntity
import com.ppidev.smartcube.data.remote.api.AuthInterceptor
import com.ppidev.smartcube.data.remote.api.SmartCubeApi
import com.ppidev.smartcube.data.remote.api.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
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
