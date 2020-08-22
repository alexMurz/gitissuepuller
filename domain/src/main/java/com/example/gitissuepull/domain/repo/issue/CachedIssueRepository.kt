package com.example.gitissuepull.domain.repo.issue

import com.example.gitissuepull.domain.cache.TimedCache
import com.example.gitissuepull.domain.cache.TimedCacheSerializer
import com.example.gitissuepull.domain.data.Issue

/// Extension of BasicIssueRepository, use HashMap to hold already loaded List<Issue> for User, Repo
/// Yield cached data on `get` call, and update cache on `load` call
class CachedIssueRepository(
    issueSource: IssueRepository.UseGet,
    cacheLoad: TimedCacheSerializer.Deserializer<String, List<Issue>>,
    cacheSave: TimedCacheSerializer.Serializer<String, List<Issue>>
): BasicIssueRepository(issueSource) {

    private val serializer = TimedCacheSerializer(cacheSave, cacheLoad)
    private val cache = TimedCache<String, List<Issue>>(10000).also {
        serializer.deserialize(it)
    }

    private fun formatName(user: String, repo: String): String = user + repo

    override fun get(user: String, repo: String) {
        val cache = cache.get(formatName(user, repo)) ?: return super.get(user, repo)
        callback { onSuccess(cache) }
    }

    override fun loadComplete(user: String, repo: String, result: List<Issue>) {
        cache.insert(formatName(user, repo), result)
        // Queue cache serialization
        serializer.serialize(cache)
        super.loadComplete(user, repo, result)
    }

    override fun loadFailed(user: String, repo: String, error: Throwable) {
        // Propagate error and cached data, if present
        cache.rawGet(formatName(user, repo))?.let {
            callback { onSuccess(it) }
        }
        super.loadFailed(user, repo, error)
    }
}