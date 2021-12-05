package com.yashkasera.aphrodite.util

import android.widget.ImageView
import androidx.core.widget.doAfterTextChanged
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.yashkasera.aphrodite.R

/**
 * @author yashkasera
 * Created 04/12/21 at 8:25 PM
 */
private const val TAG = "Functions"

fun TextInputEditText.checkNotEmpty(textInputLayout: TextInputLayout) {
    doAfterTextChanged {
        textInputLayout.isErrorEnabled = it.isNullOrEmpty()
        textInputLayout.error = if (it.isNullOrEmpty()) "Cannot be Empty!" else null
    }
}

fun ImageView.loadImage(url:String){
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.welcome_dot_default)
        .into(this)
}