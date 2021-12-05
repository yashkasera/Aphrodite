package com.yashkasera.aphrodite.model

import com.google.firebase.Timestamp
import java.io.Serializable

/**
 * @author yashkasera
 * Created 04/12/21 at 10:16 PM
 */
class Contact(
    var name: String? = null,
    var alias: String? = null,
    var lastSeen: Timestamp? = null
) : Serializable
