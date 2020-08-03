package com.example.gitissuepull.domain.repo

import com.example.gitissuepull.domain.data.Repository
import com.example.gitissuepull.domain.data.User
import com.example.gitissuepull.domain.uses.UseCaseGetUserReposPage
import io.reactivex.disposables.Disposable

/**
 * Provide `user`'s repositories page by page
 */
class UserRepoRepository(
    private val get: UseCaseGetUserReposPage
) : RepositoryBase<UserRepoRepository.Callback>() {

    companion object {
        const val PAGE_SIZE = 30
    }

    val loaded = arrayListOf<Repository>() // Already loaded
    private var task: Disposable? = null // Current/Last task
    private var user: User? = null // Current user

    fun clear() {
        loaded.clear()
        task?.dispose()
        user = null
    }

    fun setUser(user: User) {
        clear()
        this.user = user
    }

    private fun loadingCompleted() {
        this.user = null
        callback { onLoadingCompleted() }
    }

    fun loadNextPage(): Boolean {
        val user = user ?: return false
        if (loaded.size < user.publicRepos) {
            val currentPage = loaded.size / PAGE_SIZE
            task?.dispose()
            task = get.getUserReposPage(user.login, currentPage, PAGE_SIZE).subscribe({
                loaded.addAll(it)
                callback { onPageLoaded(it) }
                if (loaded.size == user.publicRepos) loadingCompleted()
            }, {
                callback { onError(it) }
            })
            return true
        } else return false
    }

    interface Callback {
        fun onLoadingCompleted()
        fun onPageLoaded(added: List<Repository>)
        fun onError(throwable: Throwable)
    }

}