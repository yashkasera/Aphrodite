package com.yashkasera.aphrodite.ui.requests

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yashkasera.aphrodite.databinding.ItemRequestBinding
import com.yashkasera.aphrodite.model.HelpRequest

/**
 * @author yashkasera
 * Created 05/12/21 at 8:11 AM
 */
private const val TAG = "RequestViewHolder"

class RequestViewHolder(private val binding: ItemRequestBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(helpRequest: HelpRequest) {
        binding.apply {
            alias.text = helpRequest.alias
            distance.text = helpRequest.distance
        }
    }

    companion object {
        fun create(parent: ViewGroup): RequestViewHolder =
            RequestViewHolder(ItemRequestBinding.inflate(LayoutInflater.from(parent.context)))
    }

}