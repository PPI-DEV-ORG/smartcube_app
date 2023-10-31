package com.ppidev.smartcube.di

import android.content.Context
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import com.ppidev.smartcube.contract.data.remote.service.IMqttService
import com.ppidev.smartcube.domain.ext_service.HiveMqttService
import com.ppidev.smartcube.domain.ext_service.WebSocketListenerService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.WebSocketListener
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    @Singleton
    abstract fun bindMqttService(
        mqttService: HiveMqttService
    ): IMqttService

    @Binds
    @Singleton
    abstract fun bindWebSocketService(
        webSocketListenerService: WebSocketListenerService
    ): WebSocketListener

}

@Singleton
class NavigationService @Inject constructor(
    @ApplicationContext context: Context,
) {
    val navController = NavHostController(context).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
        navigatorProvider.addNavigator(DialogNavigator())
    }
}