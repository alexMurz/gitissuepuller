package com.example.gitissuepull.domain.repo.issue

import com.example.gitissuepull.domain.repo.RepositoryBase
import io.reactivex.disposables.Disposable

class BasicIssueRepository(
    private val issueSource: IssueRepository.UseGet
): IssueRepository, RepositoryBase<IssueRepository.Result>()
{

    private var user: String? = null
    private var repo: String? = null
    private var task: Disposable? = null // Current/Last task

    override fun setSource(user: String, repo: String) {
        // Update with setSource only if user or repo is different
        if (this.user != user || this.repo != repo) {
            this.user = user
            this.repo = repo
            reload()
        }
    }

    override fun reload() {
        task?.dispose()
        task = issueSource.getIssues(user!!, repo!!).subscribe({
            callback { onSuccess(it) }
        }, {
            callback { onError(it) }
        })
    }

}