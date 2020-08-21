package com.example.gitissuepull.data.uses

import com.example.gitissuepull.data.api.RepositoryApi
import com.example.gitissuepull.data.entries.RepositoryEntryMapper
import com.example.gitissuepull.domain.data.Repository
import com.example.gitissuepull.domain.repo.user_repos.UserRepoRepository
import io.reactivex.Single
import javax.inject.Singleton

@Singleton class UseCaseUserReposApiGet(private val api: RepositoryApi): UserRepoRepository.UseGet {
    override fun getUserReposPage(owner: String, page: Int, perPage: Int): Single<List<Repository>> =
        api.getUserReposPage(owner, page, perPage).map { it.map(RepositoryEntryMapper) }
}