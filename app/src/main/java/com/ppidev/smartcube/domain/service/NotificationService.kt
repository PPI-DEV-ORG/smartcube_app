package com.ppidev.smartcube.domain.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat

class NotificationService(private val context: Context) {

    // Menampilkan notifikasi dengan konfigurasi yang diberikan
    fun showNotification(builder: NotificationBuilder.() -> Unit) {
        val notificationBuilder = NotificationBuilder(context)
        builder.invoke(notificationBuilder)
        val notification = notificationBuilder.build()
        displayNotification(notification)
    }

    inner class NotificationBuilder(private val context: Context) {
        private val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
        private var pendingIntent: PendingIntent? = null

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
            notificationBuilder.setSound(sound)
        }

        // Mengatur aktivitas target yang akan dijalankan saat notifikasi diklik
        fun setTargetActivity(activityClass: Class<*>, extras: Bundle? = null) {
            val intent = Intent(context, activityClass)

            extras?.let { intent.putExtra(EXTRA_NOTIFICATION, extras) }
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
//            pendingIntent = PendingIntent.getActivity(
//                context,
//                NOTIFICATION_ID,
//                intent,
//                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//            )

//            pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                PendingIntent.getActivity(
//                    context,
//                    NOTIFICATION_ID,
//                    intent,
//                    PendingIntent.FLAG_MUTABLE
//                )
//            } else {
//                PendingIntent.getActivity(
//                    context,
//                    NOTIFICATION_ID,
//                    intent,
//                    PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
//                )
//            }
        }


        fun setLargeIcon(image: Bitmap) {
            notificationBuilder.setLargeIcon(image)
        }

        fun build(): Notification {
            pendingIntent?.let {
                notificationBuilder.setContentIntent(it)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel()
            }

            return notificationBuilder.build()
        }

        private fun createNotificationChannel() {
            val channel =
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            channel.description = CHANNEL_DESC
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Menampilkan notifikasi menggunakan NotificationManager
    private fun displayNotification(notification: Notification) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val CHANNEL_ID = "smartcube_app_chanel_id"
        private const val CHANNEL_NAME = "smartcube_app_chanel_name"
        private const val CHANNEL_DESC = "channel_description"
        private const val NOTIFICATION_ID = 123
        const val EXTRA_NOTIFICATION = "extras"
    }
}