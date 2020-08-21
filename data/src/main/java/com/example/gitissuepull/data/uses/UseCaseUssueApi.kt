package com.example.gitissuepull.data.uses

import com.example.gitissuepull.data.api.IssuesApi
import com.example.gitissuepull.data.entries.IssueEntryMapper
import com.example.gitissuepull.domain.data.Issue
import com.example.gitissuepull.domain.repo.issue.IssueRepository
import io.reactivex.Single
import javax.inject.Singleton

@Singleton class UseCaseUssueApiGetIssues(private val api: IssuesApi): IssueRepository.UseGet {
    override fun getIssues(user: String, repository: String): Single<List<Issue>> =
        api.getIssues(user, repository).map { it.map(IssueEntryMapper) }
}

