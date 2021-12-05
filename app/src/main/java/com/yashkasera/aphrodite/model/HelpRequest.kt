package com.yashkasera.aphrodite.model

import com.google.gson.annotations.SerializedName

/**
 * @author yashkasera
 * Created 05/12/21 at 8:12 AM
 */
private const val TAG = "mRequest"

data class HelpRequest(
    @SerializedName("alias")
    var alias: String? = null,

    @SerializedName("distance")
    var distance: String? = null
)