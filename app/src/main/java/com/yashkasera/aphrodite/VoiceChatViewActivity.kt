package com.yashkasera.aphrodite


import android.Manifest
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.agora.rtc.Constants
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import java.util.*

private const val TAG = "VoiceChatViewActivity"

class VoiceChatViewActivity : AppCompatActivity() {
    lateinit var mRtcEngine: RtcEngine // Tutorial Step 1
    private val PERMISSION_REQ_ID_RECORD_AUDIO = 22

    val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        // Tutorial Step 1
        /**
         * Occurs when a remote user (Communication)/host (Live Broadcast) leaves the channel.
         *
         * There are two reasons for users to become offline:
         *
         * Leave the channel: When the user/host leaves the channel, the user/host sends a goodbye message. When this message is received, the SDK determines that the user/host leaves the channel.
         * Drop offline: When no data packet of the user or host is received for a certain period of time (20 seconds for the communication profile, and more for the live broadcast profile), the SDK assumes that the user/host drops offline. A poor network connection may lead to false detections, so we recommend using the Agora RTM SDK for reliable offline detection.
         *
         * @param uid ID of the user or host who
         * leaves
         * the channel or goes offline.
         * @param reason Reason why the user goes offline:
         *
         * USER_OFFLINE_QUIT(0): The user left the current channel.
         * USER_OFFLINE_DROPPED(1): The SDK timed out and the user dropped offline because no data packet was received within a certain period of time. If a user quits the call and the message is not passed to the SDK (due to an unreliable channel), the SDK assumes the user dropped offline.
         * USER_OFFLINE_BECOME_AUDIENCE(2): (Live broadcast only.) The client role switched from the host to the audience.
         */
        override fun onUserOffline(uid: Int, reason: Int) { // Tutorial Step 4
            runOnUiThread { onRemoteUserLeft(uid, reason) }
        }

        /**
         * Occurs when a remote user stops/resumes sending the audio stream.
         * The SDK triggers this callback when the remote user stops or resumes sending the audio stream by calling the muteLocalAudioStream method.
         *
         * @param uid ID of the remote user.
         * @param muted Whether the remote user's audio stream is muted/unmuted:
         *
         * true: Muted.
         * false: Unmuted.
         */
        override fun onUserMuteAudio(uid: Int, muted: Boolean) { // Tutorial Step 6
            runOnUiThread { onRemoteUserVoiceMuted(uid, muted) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_chat_view)
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO)) {
            initAgoraEngineAndJoinChannel();
        }
    }

    private fun initAgoraEngineAndJoinChannel() {
        initializeAgoraEngine() // Tutorial Step 1
        joinChannel() // Tutorial Step 2
    }

    private fun checkSelfPermission(permission: String, requestCode: Int): Boolean {
        Log.i(TAG, "checkSelfPermission $permission $requestCode")
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(permission),
                requestCode
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(TAG, "onRequestPermissionsResult " + grantResults[0] + " " + requestCode)
        when (requestCode) {
            PERMISSION_REQ_ID_RECORD_AUDIO -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    initAgoraEngineAndJoinChannel()
                } else {
                    showLongToast("No permission for " + Manifest.permission.RECORD_AUDIO)
                    finish()
                }
            }
        }
    }

    private fun showLongToast(msg: String?) {
        runOnUiThread { Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show() }
    }

    override fun onDestroy() {
        super.onDestroy()
        leaveChannel()
        RtcEngine.destroy()
//        mRtcEngine = null
    }

    // Tutorial Step 7
    fun onLocalAudioMuteClicked(view: View) {
        val iv: ImageView = view as ImageView
        if (iv.isSelected) {
            iv.isSelected = false
            iv.clearColorFilter()
        } else {
            iv.isSelected = true
            iv.setColorFilter(resources.getColor(R.color.pink_300), PorterDuff.Mode.MULTIPLY)
        }

        // Stops/Resumes sending the local audio stream.
        mRtcEngine.muteLocalAudioStream(iv.isSelected)
    }

    // Tutorial Step 5
    fun onSwitchSpeakerphoneClicked(view: View) {
        val iv: ImageView = view as ImageView
        if (iv.isSelected) {
            iv.isSelected = false
            iv.clearColorFilter()
        } else {
            iv.isSelected = true
            iv.setColorFilter(resources.getColor(R.color.teal_700), PorterDuff.Mode.MULTIPLY)
        }

        // Enables/Disables the audio playback route to the speakerphone.
        //
        // This method sets whether the audio is routed to the speakerphone or earpiece. After calling this method, the SDK returns the onAudioRouteChanged callback to indicate the changes.
        mRtcEngine.setEnableSpeakerphone(view.isSelected())
    }

    // Tutorial Step 3
    fun onEncCallClicked(view: View?) {
        finish()
    }

    // Tutorial Step 1
    private fun initializeAgoraEngine() {
        mRtcEngine = try {
            RtcEngine.create(baseContext, getString(R.string.agora_app_id), mRtcEventHandler)
        } catch (e: Exception) {
            Log.e(TAG, Log.getStackTraceString(e))
            throw RuntimeException(
                """
               NEED TO check rtc sdk init fatal error
                     ${Log.getStackTraceString(e)}
                     """.trimIndent()
            )
        }
    }

    // Tutorial Step 2
    private fun joinChannel() {
        var accessToken: String? = getString(R.string.agora_access_token)
        if (TextUtils.equals(accessToken, "") || TextUtils.equals(
                accessToken,
                ""
            )
        ) {
            accessToken = null // default, no token
        }

        mRtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_COMMUNICATION)

        mRtcEngine.joinChannel(
            accessToken,
            "test1",
            "",
            0
        ) // if you do not specify the uid, we will generate the uid for you
    }

    // Tutorial Step 3
    private fun leaveChannel() {
        mRtcEngine.leaveChannel()
    }

    // Tutorial Step 4
    private fun onRemoteUserLeft(uid: Int, reason: Int) {
        showLongToast("user uid left: $reason")
    }

    // Tutorial Step 6
    private fun onRemoteUserVoiceMuted(uid: Int, muted: Boolean) {
        showLongToast("user muted: $muted")
    }
}