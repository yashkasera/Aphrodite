package com.yashkasera.aphrodite.ui.chat

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yashkasera.aphrodite.model.Contact

/**
 * @author yashkasera
 * Created 04/12/21 at 10:17 PM
 */
private const val TAG = "ContactsAdapter"

class ContactsAdapter(private val onClickListener: ContactsAdapter.ChatClickListener) :
    ListAdapter<Contact, ContactViewHolder>(DIFF_CALLBACK) {

    companion object DIFF_CALLBACK : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
            oldItem.lastSeen == newItem.lastSeen
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder =
        ContactViewHolder.create(parent)


    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) =
        holder.bind(getItem(position), onClickListener)

    interface ChatClickListener {
        fun onClick(contact: Contact)
    }
}
