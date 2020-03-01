package com.example.gitissuepull.repo

import com.example.gitissuepull.api.GitApiAccess
import com.example.gitissuepull.entity.api.Repository
import com.example.gitissuepull.entity.api.User
import com.example.gitissuepull.repo.IUserReposRepository.*
import com.example.gitissuepull.repo.base.RepositoryBase
import io.reactivex.disposables.Disposable


// Loader class for specific user public repositories
class UserReposRepository(private val api: GitApiAccess): RepositoryBase<Listener>(), IUserReposRepository {
    companion object {
        private const val PAGE_SIZE = 30
    }

    private var task: Disposable? = null // Current/Last task
    private var user: User? = null // Currently loading this user's repos
    private val loaded = arrayListOf<Repository>()

    override val loadedRepositories: List<Repository> get() = loaded

    override fun setCurrentUser(userName: String) {
        loaded.clear()
        task?.dispose()
        task = api.getUser(userName).subscribe({
            user = it
            callback { onUserInfoLoaded(it) }
        }, {
            callback { onError(it) }
        })
    }

    override fun loadNextPage(): Boolean {
        // if user is null -> Loading completed
        val user = user ?: return false
        if (loaded.size < user.publicRepos) {
            val currentPage = loaded.size / PAGE_SIZE
            task?.dispose()
            task = api.getUserRepos(user.login, currentPage, PAGE_SIZE).subscribe({
                loaded.addAll(it)
                callback { onPageLoaded(it) }
                if (loaded.size == user.publicRepos) loadingCompleted()
            }, {
                callback { onError(it) }
            })
            return true
        } else return false

    }

    private fun loadingCompleted() {
        user = null
        callback { onLoadingCompleted() }
    }


}