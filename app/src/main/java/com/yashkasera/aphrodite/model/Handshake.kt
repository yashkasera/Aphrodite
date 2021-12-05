package com.yashkasera.aphrodite.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author yashkasera
 * Created 04/12/21 at 11:53 PM
 */
data class Handshake(
    var sender: String? = null,
    var receiver: String? = null,
    var createdAt: Date? = null
){
    @SerializedName("_id")
    var id: String? = null
}
