package com.example.blooddonation.home.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.blooddonation.databinding.ItemViewBinding


class SearchAdapter
    :ListAdapter<FakeClass,SearchAdapter.FakeViewHolder>(DiffCallback) {

    class FakeViewHolder  private constructor(private val binding:ItemViewBinding)
        :RecyclerView.ViewHolder(binding.root) {
        fun bind(fakeClass: FakeClass) {

            // bind xml views
            //binding.fakeClass=FakeClass

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FakeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemViewBinding.inflate(layoutInflater, parent, false)
                return FakeViewHolder(binding)
            }
        }
    }

    companion object DiffCallback:DiffUtil.ItemCallback<FakeClass>() {
        override fun areItemsTheSame(oldItem: FakeClass, newItem: FakeClass): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: FakeClass, newItem: FakeClass): Boolean {
            return oldItem.id==newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FakeViewHolder {
       return FakeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FakeViewHolder, position: Int) {
        val fakeClass=getItem(position)
        holder.bind(fakeClass)
    }

}

data class FakeClass (val id:String)

