package com.example.gitissuepull.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gitissuepull.R
import com.example.gitissuepull.databinding.ListFragmentItemBinding
import com.example.gitissuepull.entity.api.Issue

class ListAdapter: RecyclerView.Adapter<ListAdapter.Holder>() {

    val items = ArrayList<Issue>()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = Holder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_fragment_item, parent, false))

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.set(items[position])
    }

    class Holder(val binding: ListFragmentItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun set(data: Issue) { binding.issue = data }
    }

}