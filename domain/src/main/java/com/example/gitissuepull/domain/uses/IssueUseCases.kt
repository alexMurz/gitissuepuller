package com.example.gitissuepull.domain.uses

import com.example.gitissuepull.domain.data.Issue
import io.reactivex.Single


interface UseCaseGetIssues {
    fun getIssues(user: String, repository: String): Single<List<Issue>>
}
