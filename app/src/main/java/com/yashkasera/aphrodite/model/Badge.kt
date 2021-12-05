package com.yashkasera.aphrodite.model

import com.google.gson.annotations.SerializedName

/**
 * @author yashkasera
 * Created 05/12/21 at 6:07 AM
 */

data class Badge(
    @SerializedName("name")
    var name: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("image")
    var image: String? = null
){
    @SerializedName("_id")
    var id: String? = null

}