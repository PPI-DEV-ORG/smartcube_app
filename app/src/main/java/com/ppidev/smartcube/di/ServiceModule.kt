package com.ppidev.smartcube.di

import android.content.Context
import com.ppidev.smartcube.contract.data.remote.service.IMqttService
import com.ppidev.smartcube.domain.ext_service.MqttService
import com.ppidev.smartcube.domain.ext_service.WebSocketListenerService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.WebSocketListener
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    @Singleton
    abstract fun bindWebSocketService(
        webSocketListenerService: WebSocketListenerService
    ): WebSocketListener
}

@Module
@InstallIn(SingletonComponent::class)
class ProvideServiceModule {
//    @Singleton
//    @Provides
//    fun provideHiveMqttService(
//        @ApplicationContext context: Context,
//    ): IMqttService {
//        return HiveMqttService(context)
//    }

    @Singleton
    @Provides
    fun provideMqttService(
        @ApplicationContext context: Context,
    ): IMqttService {
        return MqttService(context)
    }
}