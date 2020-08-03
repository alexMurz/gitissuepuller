package com.example.gitissuepull.data.api

import com.example.gitissuepull.data.entries.IssueEntry
import io.reactivex.Single

interface IssuesApi {
    fun getIssues(owner: String, repo: String): Single<List<IssueEntry>>
}