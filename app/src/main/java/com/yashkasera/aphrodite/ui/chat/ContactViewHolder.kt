package com.yashkasera.aphrodite.ui.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yashkasera.aphrodite.databinding.ItemContactBinding
import com.yashkasera.aphrodite.model.Contact
import java.text.DateFormat

/**
 * @author yashkasera
 * Created 04/12/21 at 10:18 PM
 */
private const val TAG = "ContactViewHolder"
class ContactViewHolder(private val binding: ItemContactBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(contact: Contact, onClickListener: ContactsAdapter.ChatClickListener) {
        binding.apply {
            root.setOnClickListener{
                onClickListener.onClick(contact)
                Log.i(TAG, "bind: $contact")
            }
            name.text = contact.name
            time.text = DateFormat.getInstance().format(contact.lastSeen?.seconds).toString()
        }
    }

    companion object {
        fun create(parent: ViewGroup): ContactViewHolder =
            ContactViewHolder(ItemContactBinding.inflate(LayoutInflater.from(parent.context)))
    }

}