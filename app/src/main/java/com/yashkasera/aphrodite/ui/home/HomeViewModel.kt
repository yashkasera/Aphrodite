package com.yashkasera.aphrodite.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yashkasera.aphrodite.MainActivity
import com.yashkasera.aphrodite.service.RetrofitService
import kotlinx.coroutines.launch

/**
 * @author yashkasera
 * Created 05/12/21 at 6:51 AM
 */
private const val TAG = "HomeViewModel"

class HomeViewModel : ViewModel() {
    private val retrofitService by lazy { RetrofitService.create() }

    fun SOS() {
        viewModelScope.launch {
            try {
                val res = retrofitService.sos(MainActivity.authToken)
                if (res.isSuccessful) {
                    Log.d(TAG, "SOS() returned: ${res.body()}")
                }else{
                    Log.i(TAG, "SOS: ${res.code()} ${res.errorBody()}")

                }
            } catch (e: Exception) {
                Log.e(TAG, "SOS: ", e)
            }
        }
    }
}