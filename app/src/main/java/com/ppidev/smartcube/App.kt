package com.ppidev.smartcube

import android.app.Application
import android.os.Build
import com.ppidev.smartcube.utils.manager.NotificationChannelManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannelManager.apply {
                createNotificationChannelFCM(applicationContext)
                createNotificationChannelTest(applicationContext)
            }
        }
    }
}