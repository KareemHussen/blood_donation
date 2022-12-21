package com.example.blooddonation.home.request

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.blooddonation.databinding.ItemViewBinding
import com.example.blooddonation.home.request.model.RequestModel

class RequestsAdapter(private val clickListener: RequestListener) :
    ListAdapter<RequestModel, RequestsAdapter.RequestViewHolder>(RequestDiff()) {


    class RequestViewHolder(private val binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(request: RequestModel) {
            binding.request = request
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.also {
            it.itemView.setOnClickListener {
                clickListener.onClick(currentItem)
            }
            it.bind(currentItem)
        }
    }


    class RequestDiff : DiffUtil.ItemCallback<RequestModel>() {

        override fun areItemsTheSame(oldItem: RequestModel, newItem: RequestModel): Boolean =
            oldItem === newItem


        override fun areContentsTheSame(oldItem: RequestModel, newItem: RequestModel): Boolean =
            oldItem == newItem


    }

    class RequestListener(val clickListener: (request: RequestModel) -> Unit) {
        fun onClick(request: RequestModel) = clickListener(request)
    }

}