package com.yashkasera.aphrodite.service

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.yashkasera.aphrodite.util.FCM_TOKEN
import com.yashkasera.aphrodite.util.SharedPrefUtil.getPrefs

/**
 * @author yashkasera
 * Created 05/12/21 at 2:22 AM
 */
private const val TAG = "FirebaseMessagingService"

class FCMService : FirebaseMessagingService() {

    override fun handleIntent(intent: Intent) {
        super.handleIntent(intent)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.i(TAG, "onMessageReceived: $remoteMessage")
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }
    }

    override fun onMessageSent(p0: String) {
        super.onMessageSent(p0)
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.i(TAG, "onNewToken: $p0")
        getPrefs(this).edit().putString(FCM_TOKEN, p0).apply()
    }
}