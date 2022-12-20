package com.example.blooddonation.home.request

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.blooddonation.databinding.ItemViewBinding

class RequestsAdapter(private val clickListener: RequestListener) :
    ListAdapter<Request, RequestsAdapter.RequestViewHolder>(RequestDiff()) {


    class RequestViewHolder(private val binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(request: Request) {
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


    class RequestDiff : DiffUtil.ItemCallback<Request>() {

        override fun areItemsTheSame(oldItem: Request, newItem: Request): Boolean =
            oldItem === newItem


        override fun areContentsTheSame(oldItem: Request, newItem: Request): Boolean =
            oldItem == newItem


    }

    class RequestListener(val clickListener: (request: Request) -> Unit) {
        fun onClick(request: Request) = clickListener(request)
    }

}