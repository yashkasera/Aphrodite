package com.yashkasera.aphrodite.ui.chat

import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase

/**
 * @author yashkasera
 * Created 04/12/21 at 10:06 PM
 */
private const val TAG = "ChatViewModel"

class ChatViewModel : ViewModel() {
    private val db: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    val messagesRef = db.reference.child("messages")

}