
package com.example.tasktwo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktwo.databinding.ItemLayoutBinding

class MyPagingAdapter :
    PagingDataAdapter<MyItem, MyPagingAdapter.MyViewHolder>(MyItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    inner class MyViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MyItem) {
            // Bind your data to the View Binding views
            binding.textTitle.text = item.sportName
            binding.textDescription.text = item.sportDescription
        }
    }

    private class MyItemDiffCallback : DiffUtil.ItemCallback<MyItem>() {
        override fun areItemsTheSame(oldItem: MyItem, newItem: MyItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MyItem, newItem: MyItem): Boolean {
            return oldItem == newItem
        }
    }
}
