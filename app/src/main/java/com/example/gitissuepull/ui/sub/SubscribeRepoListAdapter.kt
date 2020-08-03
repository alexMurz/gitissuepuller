package com.example.gitissuepull.ui.sub

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gitissuepull.R
import com.example.gitissuepull.databinding.SubscribeActivityItemBinding

class SubscribeRepoListAdapter: RecyclerView.Adapter<SubscribeRepoListAdapter.Holder>() {

    var eventListener: ((RepositoryViewData) -> Unit)? = null

    val items = ArrayList<RepositoryViewData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.subscribe_activity_item, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.set(items[position])
    }

    override fun getItemCount() = items.size

    inner class Holder(val binding: SubscribeActivityItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun set(data: RepositoryViewData) {
            binding.listener = this
            binding.repo = data
        }
        fun onClick() {
            eventListener?.invoke(binding.repo!!)
            println("Repo flag: ${binding.repo!!.isSubbed}")
            binding.repo = binding.repo // Eh

        }
    }

}