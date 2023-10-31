package com.ppidev.smartcube.domain.ext_service

import android.net.Uri
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ppidev.smartcube.R
import com.ppidev.smartcube.contract.data.remote.service.IMessagingService
import com.ppidev.smartcube.contract.data.repository.ITokenAppRepository
import com.ppidev.smartcube.data.local.entity.ENotificationChannel
import com.ppidev.smartcube.data.remote.dto.FcmMessage
import com.ppidev.smartcube.utils.getBitmapFromUrl
import com.ppidev.smartcube.utils.manager.MyNotificationManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FcmService: FirebaseMessagingService(), IMessagingService<RemoteMessage> {
    @Inject
    lateinit var tokenAppRepositoryImpl: ITokenAppRepository

    private lateinit var notificationManager: MyNotificationManager
    private lateinit var soundUri: Uri

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

        // save token fcm to local datastore
        CoroutineScope(Dispatchers.IO).launch {
            saveFcmTokenToLocal(token)
        }

        storeFcmTokenToAPI(token)
    }

    override fun handleOnMessageReceived(messages: RemoteMessage) {
        messages.data.apply {
            val imageUrl = this[KEY_IMAGE_URL].toString()
            val notificationTitle = this[KEY_TITLE].toString()
            val notificationBody = this[KEY_BODY].toString()

            // show notification
            showNotification(FcmMessage(notificationTitle, notificationBody, imageUrl))
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
            setSound(soundUri)

            bitmapImage?.apply {
                setStyle(
                    NotificationCompat.BigPictureStyle().bigPicture(this).bigLargeIcon(null)
                )
                setLargeIcon(this)
            }
        }
    }

    override fun setupMessagingService() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
        })
    }

    private fun storeFcmTokenToAPI(token: String) {
        // save token to backend [NOT IMPLEMENT]
        return
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