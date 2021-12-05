package com.yashkasera.aphrodite.util

import android.content.Context
import android.content.SharedPreferences

/**
 * @author yashkasera
 * Created 05/12/21 at 2:58 AM
 */
private const val TAG = "SharedPrefUtil"

const val SHARED_PREFERENCES = "Aphrodite"
const val FCM_TOKEN = "fcm_token"
const val IS_FIRST_LAUNCH = "isFirstLaunch"

object SharedPrefUtil {

    fun getPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

}