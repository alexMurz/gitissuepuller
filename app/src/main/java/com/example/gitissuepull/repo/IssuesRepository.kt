package com.example.gitissuepull.repo

import com.example.gitissuepull.api.GitApiAccess
import com.example.gitissuepull.repo.base.RepositoryBase
import io.reactivex.disposables.Disposable

class IssuesRepository(private val api: GitApiAccess): RepositoryBase<IIssuesRepository.Listener>(), IIssuesRepository {

    private var user: String? = null
    private var repo: String? = null
    private var task: Disposable? = null // Current/Last task

    override fun setSource(user: String, repo: String) {
        // Update with setSource only if user or repo is different
        if (this.user != user || this.repo != repo) {
            this.user = user
            this.repo = repo
            reloadData()
        }
    }

    override fun reloadData() {
        task?.dispose()
        task = api.getIssues(user!!, repo!!).subscribe({
            callback { onSuccess(it) }
        }, {
            callback { onError(it) }
        })
    }

}