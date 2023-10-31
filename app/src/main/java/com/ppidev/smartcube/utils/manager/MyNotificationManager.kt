package com.ppidev.smartcube.utils.manager

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.ppidev.smartcube.data.local.entity.ENotificationChannel

class MyNotificationManager(private val context: Context) {
    fun showNotification(channel: ENotificationChannel, builder: NotificationBuilder.() -> Unit) {
        val notificationBuilder = NotificationBuilder(context, channel.idName)

        builder.invoke(notificationBuilder)

        val notification = notificationBuilder.build()

        displayNotification(notification)
    }

    inner class NotificationBuilder(private val context: Context, channelId: String) {
        private val notificationBuilder = NotificationCompat.Builder(context, channelId)
        private var pendingIntent: PendingIntent? = null
        private var intent: Intent? = null

        fun setTitle(title: String) {
            notificationBuilder.setContentTitle(title)
        }

        fun setBody(body: String) {
            notificationBuilder.setContentText(body)
        }

        fun setSmallIcon(iconResId: Int) {
            notificationBuilder.setSmallIcon(iconResId)
        }

        fun setChannelId(id: String) {
            notificationBuilder.setChannelId(id)
        }

        fun setAutoCancel(cancel: Boolean) {
            notificationBuilder.setAutoCancel(cancel)
        }

        fun setCategory(notificationCategory: String) {
            notificationBuilder.setCategory(notificationCategory)
        }

        fun setStyle(style: NotificationCompat.Style) {
            notificationBuilder.setStyle(style)
        }

        fun setVibrate(vibrate: LongArray) {
            notificationBuilder.setVibrate(vibrate)
        }

        fun setSound(sound: Uri) {
            notificationBuilder.setSound(
                sound
            )
        }

        fun setIntent(customIntent: Intent){
            intent = customIntent
        }

        fun setPendingIntent(customPendingIntent: PendingIntent) {
            pendingIntent = customPendingIntent
        }

        fun setLargeIcon(image: Bitmap) {
            notificationBuilder.setLargeIcon(image)
        }

        fun build(): Notification {
            pendingIntent.let {
                notificationBuilder.setContentIntent(it)
            }

            return notificationBuilder.build()
        }
    }

    private fun displayNotification(notification: Notification) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val NOTIFICATION_ID = 123
        const val EXTRA_NOTIFICATION = "extras"
    }
}