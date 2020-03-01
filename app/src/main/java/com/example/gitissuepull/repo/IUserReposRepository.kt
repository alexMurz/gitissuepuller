package com.example.gitissuepull.repo

import com.example.gitissuepull.entity.api.Repository
import com.example.gitissuepull.entity.api.User
import com.example.gitissuepull.repo.base.IRepositoryBase

// Provider of all specific user repositories
interface IUserReposRepository: IRepositoryBase<IUserReposRepository.Listener> {
    interface Listener {
        fun onError(e: Throwable)
        fun onUserInfoLoaded(user: User)
        fun onPageLoaded(page: List<Repository>)
        fun onLoadingCompleted()
    }

    val loadedRepositories: List<Repository>

    fun setCurrentUser(userName: String)

    // Return true if loading begun
    fun loadNextPage(): Boolean
}