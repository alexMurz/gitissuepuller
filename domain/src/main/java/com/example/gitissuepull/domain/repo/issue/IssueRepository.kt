package com.example.gitissuepull.domain.repo.issue

import com.example.gitissuepull.domain.data.Issue
import com.example.gitissuepull.domain.repo.IRepositoryBase
import io.reactivex.Single

interface IssueRepository: IRepositoryBase<IssueRepository.Result> {

    fun setSource(user: String, repo: String)
    fun reload()

    // Use cases
    interface UseGet {
        fun getIssues(user: String, repository: String): Single<List<Issue>>
    }

    // Callback for listeners
    interface Result {
        fun onSuccess(list: List<Issue>)
        fun onError(error: Throwable)
    }
}
