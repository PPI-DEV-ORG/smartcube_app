package com.ppidev.smartcube.domain.ext_service

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ppidev.smartcube.R
import com.ppidev.smartcube.common.APP_URL
import com.ppidev.smartcube.common.EExceptionCode
import com.ppidev.smartcube.common.NOTIFICATION_ARG
import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.contract.data.remote.service.IMessagingService
import com.ppidev.smartcube.contract.data.repository.ITokenAppRepository
import com.ppidev.smartcube.contract.data.repository.IUserRepository
import com.ppidev.smartcube.data.local.entity.ENotificationChannel
import com.ppidev.smartcube.data.remote.dto.FcmMessage
import com.ppidev.smartcube.presentation.MainActivity
import com.ppidev.smartcube.ui.Screen
import com.ppidev.smartcube.utils.getBitmapFromUrl
import com.ppidev.smartcube.utils.manager.MyNotificationManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FcmService : FirebaseMessagingService(), IMessagingService<RemoteMessage> {
    @Inject
    lateinit var tokenAppRepositoryImpl: ITokenAppRepository

    @Inject
    lateinit var userRepository: IUserRepository

    private lateinit var notificationManager: MyNotificationManager

    override fun onCreate() {
        super.onCreate()
        notificationManager = MyNotificationManager(applicationContext)
        setupMessagingService()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        handleOnMessageReceived(remoteMessage)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // save token fcm to local datastore and api
        CoroutineScope(Dispatchers.IO).launch {
            saveFcmTokenToLocal(token)
            storeFcmTokenToAPI(token)
        }
        Log.d("FCM", "token $token")
    }

    override fun handleOnMessageReceived(messages: RemoteMessage) {
        Log.d("MESSAGE_FCM", messages.data.toString())
        messages.data.apply {
            val imageUrl = this[KEY_IMAGE_URL].toString()
            val notificationTitle = this[KEY_TITLE].toString()
            val notificationBody = this[KEY_BODY].toString()
            val notificationId = this["notificationId"].toString()

            // show notification
            showNotification(
                FcmMessage(
                    notificationTitle,
                    notificationBody,
                    imageUrl,
                    notificationId
                )
            )
        }
    }

    override fun showNotification(data: FcmMessage) {
        // convert image url to bitmap format
        val bitmapImage = getBitmapFromUrl(data.imageUrl)

        notificationManager.showNotification(channel = ENotificationChannel.FCM_CHANNEL) {
            setTitle(data.title)
            setBody(data.description)
            setSmallIcon(R.drawable.ic_stat_notification)
            setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))

            bitmapImage?.apply {
                setStyle(
                    NotificationCompat.BigPictureStyle().bigPicture(this)
                )
                setLargeIcon(this)
            }

            val intent = Intent(
                Intent.ACTION_VIEW,
                "$APP_URL/${Screen.Notifications.screenRoute}".toUri(),
                applicationContext,
                MainActivity::class.java
            )

            val flag =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    PendingIntent.FLAG_IMMUTABLE
                else
                    0

            setIntent(
                intent
            )

            setPendingIntent(
                TaskStackBuilder.create(applicationContext).run {
                    addNextIntentWithParentStack(intent)
                    getPendingIntent(1, flag)
                }
            )
        }
    }

    override fun setupMessagingService() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
        })
    }

    private suspend fun storeFcmTokenToAPI(token: String): ResponseApp<Any?> {
        return try {
            val response = userRepository.updateFcmToken(token)
            response
        } catch (e: Exception) {
            ResponseApp(
                status = false,
                statusCode = EExceptionCode.UseCaseError.code,
                message = e.message ?: "Service error",
                data = null
            )
        }
    }

    private suspend fun saveFcmTokenToLocal(token: String) {
        tokenAppRepositoryImpl.updateFcmToken(token)
    }

    companion object {
        private val TAG = FcmService::class.simpleName
        private const val KEY_IMAGE_URL = "imageUrl"
        private const val KEY_TITLE = "title"
        private const val KEY_BODY = "description"
    }
}