package com.example.gitissuepull.domain.repo.issue

import com.example.gitissuepull.domain.data.Issue

/// Extension of BasicIssueRepository, use HashMap to hold already loaded List<Issue> for User, Repo
/// Yield cached data on `get` call, and update cache on `load` call
class CachedIssueRepository(
    issueSource: IssueRepository.UseGet
): BasicIssueRepository(issueSource) {

    private val cache = HashMap<String, List<Issue>>()
    private fun formatName(user: String, repo: String): String = user + repo

    override fun get(user: String, repo: String) {
        val cache = cache[formatName(user, repo)] ?: return super.get(user, repo)
        callback { onSuccess(cache) }
    }

    override fun loadComplete(user: String, repo: String, result: List<Issue>) {
        cache[formatName(user, repo)] = result
        super.loadComplete(user, repo, result)
    }

}