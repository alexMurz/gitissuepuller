package com.example.gitissuepull.domain.repo

import com.example.gitissuepull.domain.data.Issue
import com.example.gitissuepull.domain.uses.UseCaseGetIssues
import io.reactivex.disposables.Disposable

class IssueRepository(
    private val issueSource: UseCaseGetIssues
): RepositoryBase<IssueRepository.Result>() {

    private var user: String? = null
    private var repo: String? = null
    private var task: Disposable? = null // Current/Last task

    fun setSource(user: String, repo: String) {
        // Update with setSource only if user or repo is different
        if (this.user != user || this.repo != repo) {
            this.user = user
            this.repo = repo
            reloadData()
        }
    }

    fun reloadData() {
        task?.dispose()
        task = issueSource.getIssues(user!!, repo!!).subscribe({
            callback { onSuccess(it) }
        }, {
            callback { onError(it) }
        })
    }

    interface Result {
        fun onSuccess(list: List<Issue>)
        fun onError(error: Throwable)
    }

}