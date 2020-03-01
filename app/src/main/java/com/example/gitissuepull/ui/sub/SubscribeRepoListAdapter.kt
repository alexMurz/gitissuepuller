package com.example.gitissuepull.ui.sub

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gitissuepull.R
import com.example.gitissuepull.databinding.SubscribeActivityItemBinding
import com.example.gitissuepull.entity.api.Repository

class SubscribeRepoListAdapter: RecyclerView.Adapter<SubscribeRepoListAdapter.Holder>() {

    var eventListener: ((Repository) -> Unit)? = null

    val items = ArrayList<Repository>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.subscribe_activity_item, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.set(items[position])
    }

    override fun getItemCount() = items.size

    inner class Holder(val binding: SubscribeActivityItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun set(data: Repository) {
            binding.listener = this
            binding.repo = data
        }
        fun onClick() {
            eventListener?.invoke(binding.repo!!)
            println("Repo flag: ${binding.repo!!.subCheck}")
//            binding.notifyChange()
//            binding.executePendingBindings()
//            binding.notifyPropertyChanged(BR.repo)
            binding.repo = binding.repo // Eh

        }
    }

}