package com.yashkasera.aphrodite.ui.requests

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yashkasera.aphrodite.model.HelpRequest

/**
 * @author yashkasera
 * Created 05/12/21 at 8:11 AM
 */
private const val TAG = "RequestAdapter"

class RequestAdapter : ListAdapter<HelpRequest, RequestViewHolder>(DIFF_CALLBACK){

    object DIFF_CALLBACK : DiffUtil.ItemCallback<HelpRequest>() {
        override fun areItemsTheSame(oldItem: HelpRequest, newItem: HelpRequest): Boolean =
            oldItem.alias == newItem.alias


        override fun areContentsTheSame(oldItem: HelpRequest, newItem: HelpRequest): Boolean =
            oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder =
        RequestViewHolder.create(parent)

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) =
        holder.bind(getItem(position))

}