package com.yashkasera.aphrodite.model

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author yashkasera
 * Created 05/12/21 at 12:07 AM
 */

data class User(
    @SerializedName("name")
    var name: String? = null,

    @SerializedName("phoneNumber")
    var phoneNumber: String? = null,

    @SerializedName("firebaseId")
    var firebaseId: String? = null,

    @SerializedName("email")
    var email: String? = null,

    @SerializedName("lastSeen")
    var lastSeen: Date? = null,

    @SerializedName("agoraToken")
    var agoraToken: String? = null,

    @SerializedName("deviceId")
    var deviceId: String? = null,

    @SerializedName("fcmToken")
    var fcmToken: String? = null,

    @SerializedName("badges")
    var badges: ArrayList<Badge>?

){
    @SerializedName("_id")
    var id: String? = null

}