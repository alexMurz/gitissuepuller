package com.example.gitissuepull.repo

import com.example.gitissuepull.entity.api.Issue
import com.example.gitissuepull.repo.base.IRepositoryBase


// Provide Issues page for specific user/repo
interface IIssuesRepository: IRepositoryBase<IIssuesRepository.Listener> {
    interface Listener {
        fun onSuccess(l: List<Issue>)
        fun onError(l: Throwable)
    }

    fun setSource(user: String, repo: String)
    fun reloadData()

}