package com.example.gitissuepull.data.api

import com.example.gitissuepull.data.entries.RepositoryEntry
import io.reactivex.Single

interface RepositoryApi {
    fun getUserReposPage(owner: String, page: Int, perPage: Int): Single<List<RepositoryEntry>>
}