package com.ppidev.smartcube.domain.service

import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ppidev.smartcube.MainActivity
import com.ppidev.smartcube.R
import com.ppidev.smartcube.contract.service.IMessagingService
import com.ppidev.smartcube.data.local.dataclass.NotificationEntity
import com.ppidev.smartcube.data.local.storage.TokenAppDatastorePreferenceManager
import com.ppidev.smartcube.data.local.storage.appTokenDataStore
import com.ppidev.smartcube.utils.getBitmapFromUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FcmService : FirebaseMessagingService(), IMessagingService<RemoteMessage> {
    private lateinit var notificationManager: NotificationService
    private lateinit var tokenAppDatastoreManager: TokenAppDatastorePreferenceManager

    override fun onCreate() {
        super.onCreate()

        notificationManager = NotificationService(applicationContext)
        tokenAppDatastoreManager =
            TokenAppDatastorePreferenceManager.getInstance(applicationContext.appTokenDataStore)
        setupMessagingService()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        handleOnMessageReceived(remoteMessage)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("newToken", token);
        CoroutineScope(Dispatchers.IO).launch {
            saveFcmTokenToLocal(token)
        }
        storeFcmTokenToAPI(token)
    }

    override fun handleOnMessageReceived(messages: RemoteMessage) {
        // val notificationTitle = remoteMessage.notification?.title ?: "Default Title"
        // val notificationBody = remoteMessage.notification?.body ?: "Default Body"
        // val smallImageUrl = remoteMessage.notification?.imageUrl
        messages.data.apply {
             Log.d(TAG, "Message data payload: ${messages.data[KEY_IMAGE_URL]}")
            val imageUrl = this[KEY_IMAGE_URL]
            val notificationTitle = this[KEY_TITLE].toString()
            val notificationBody = this[KEY_BODY].toString()

            val bitmapImage = getBitmapFromUrl(imageUrl.toString())
            Log.d(TAG, bitmapImage.toString())
            showNotification(NotificationEntity(notificationTitle, notificationBody, bitmapImage))
        }
    }

    override fun showNotification(data: NotificationEntity) {
        notificationManager.showNotification {
            setTitle(data.title)
            setBody(data.description)
            setSmallIcon(R.drawable.ic_stat_notification)
            setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            setTargetActivity(MainActivity::class.java)

            data.bitmapImage?.apply {
                setStyle(
                    NotificationCompat.BigPictureStyle().bigPicture(this).bigLargeIcon(null)
                )
                setLargeIcon(this)
            }
        }
    }

    override fun onNotificationClicked() {
        return
    }

    override fun setupMessagingService() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                // Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
        })
    }

    private fun storeFcmTokenToAPI(token: String) {
        return
    }

    private suspend fun saveFcmTokenToLocal(token: String) {
        tokenAppDatastoreManager.updateFcmToken(token)
    }

    companion object {
         private val TAG = FcmService::class.simpleName
        private const val KEY_IMAGE_URL = "imageUrl"
        private const val KEY_TITLE = "title"
        private const val KEY_BODY = "description"
    }
}