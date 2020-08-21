package com.example.gitissuepull.domain.repo.issue

import com.example.gitissuepull.domain.data.Issue
import com.example.gitissuepull.domain.repo.RepositoryBase
import io.reactivex.disposables.Disposable

open class BasicIssueRepository(
    private val issueSource: IssueRepository.UseGet
): IssueRepository, RepositoryBase<IssueRepository.Result>()
{

    private var task: Disposable? = null // Current/Last task

    override fun load(user: String, repo: String) {
        task?.dispose()
        task = issueSource.getIssues(user, repo).subscribe(
            { loadComplete(user, repo, it) },
            { loadFailed(user, repo, it) }
        )
    }

    open fun loadComplete(user: String, repo: String, result: List<Issue>) {
        callback { onSuccess(result) }
    }

    open fun loadFailed(user: String, repo: String, error: Throwable) {
        callback { onError(error) }
    }

}