package com.ppidev.smartcube.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import com.ppidev.smartcube.R

object NotificationChannelManager {
    fun createNotificationChannelFCM(context: Context) {
        val soundUri =
            Uri.parse("${ContentResolver.SCHEME_ANDROID_RESOURCE}://${context.packageName}/${R.raw.fire_alarm}")

        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()

        val channel =
            NotificationChannel(
                ENotificationChannel.FCM_CHANNEL.idName,
                ENotificationChannel.FCM_CHANNEL.channelName,
                NotificationManager.IMPORTANCE_HIGH
            )

        channel.apply {
            description = ENotificationChannel.FCM_CHANNEL.desc
            setSound(soundUri, audioAttributes)
            enableVibration(true)
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    fun createNotificationChannelTest(context: Context) {
        val channel =
            NotificationChannel(
                ENotificationChannel.TEST_CHANNEL.idName,
                ENotificationChannel.TEST_CHANNEL.channelName,
                NotificationManager.IMPORTANCE_HIGH
            )

        channel.apply {
            description = ENotificationChannel.TEST_CHANNEL.desc
            enableVibration(true)
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }
}