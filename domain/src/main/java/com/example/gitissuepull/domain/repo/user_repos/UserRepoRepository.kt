package com.example.gitissuepull.domain.repo.user_repos

import com.example.gitissuepull.domain.data.Repository
import com.example.gitissuepull.domain.data.User
import com.example.gitissuepull.domain.repo.IRepositoryBase
import io.reactivex.Single

interface UserRepoRepository: IRepositoryBase<UserRepoRepository.Callback> {

    fun list(): List<Repository> // List of currently loaded
    fun clear()
    fun setUser(user: User)
    fun loadNextPage(): Boolean

    // Uses
    interface UseGet {
        fun getUserReposPage(owner: String, page: Int, perPage: Int): Single<List<Repository>>
    }

    // Callback
    interface Callback {
        fun onLoadingCompleted()
        fun onPageLoaded(added: List<Repository>)
        fun onError(throwable: Throwable)
    }
}
