package com.example.gitissuepull.domain.repo.user_repos

import com.example.gitissuepull.domain.data.Repository
import com.example.gitissuepull.domain.data.User
import com.example.gitissuepull.domain.repo.RepositoryBase
import io.reactivex.disposables.Disposable

/**
 * Provide `user`'s repositories page by page
 */
class BasicUserRepoRepository(
    private val get: UserRepoRepository.UseGet
) : UserRepoRepository, RepositoryBase<UserRepoRepository.Callback>() {

    companion object {
        const val PAGE_SIZE = 30
    }

    private val loaded = arrayListOf<Repository>() // Already loaded
    private var task: Disposable? = null // Current/Last task
    private var user: User? = null // Current user

    override fun list(): List<Repository> = loaded
    override fun clear() {
        loaded.clear()
        task?.dispose()
        user = null
    }

    override fun setUser(user: User) {
        clear()
        this.user = user
    }

    private fun loadingCompleted() {
        this.user = null
        callback { onLoadingCompleted() }
    }

    override fun loadNextPage(): Boolean {
        val user = user ?: return false
        if (loaded.size < user.publicRepos) {
            val currentPage = loaded.size / PAGE_SIZE
            task?.dispose()
            task = get.getUserReposPage(user.login, currentPage,
                PAGE_SIZE
            ).subscribe({
                loaded.addAll(it)
                callback { onPageLoaded(it) }
                if (loaded.size == user.publicRepos) loadingCompleted()
            }, {
                callback { onError(it) }
            })
            return true
        } else return false
    }

}