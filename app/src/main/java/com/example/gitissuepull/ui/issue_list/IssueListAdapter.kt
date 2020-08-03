package com.example.gitissuepull.ui.issue_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gitissuepull.R
import com.example.gitissuepull.databinding.IssueListFragmentItemBinding
import com.example.gitissuepull.domain.data.Issue

class IssueListAdapter: RecyclerView.Adapter<IssueListAdapter.Holder>() {

    val items = ArrayList<Issue>()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = Holder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.issue_list_fragment_item, parent, false))

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.set(items[position])
    }

    class Holder(val binding: IssueListFragmentItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun set(data: Issue) { binding.issue = data }
    }

}