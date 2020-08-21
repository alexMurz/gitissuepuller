package com.example.gitissuepull.ui.sub

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitissuepull.domain.data.Repository
import com.example.gitissuepull.domain.data.User
import com.example.gitissuepull.domain.repo.subscriptions.SubscriptionsRepository
import com.example.gitissuepull.domain.repo.user_repos.UserRepoRepository
import com.example.gitissuepull.domain.repo.users.UsersRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SubscribeViewModel: ViewModel(), UserRepoRepository.Callback {

    @Inject
    lateinit var subManager: SubscriptionsRepository
    @Inject
    lateinit var usersRepository: UsersRepository
    @Inject
    lateinit var userReposRepository: UserRepoRepository

    val disposable = CompositeDisposable()
    val isLoading = MutableLiveData(false)
    val loadedRepos = MutableLiveData<List<RepositoryViewData>>()
    // Error callback
    val error = MutableLiveData<Throwable>()

    fun attach() { userReposRepository.addListener(this) }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
        userReposRepository.removeListener(this)
    }

    fun startLoading(name: String) {
        loadedRepos.value = null
        isLoading.value = true
        // Load user for name
        disposable.add(usersRepository.getUser(name).subscribe(
            { userLoaded(it) },
            { userError(it) }
        ))
    }

    private fun loadNextPage() {
        // Await previous task, whatever that is
        if (isLoading.value == true) return
        if (userReposRepository.loadNextPage())
            isLoading.value = true
    }

    fun userLoaded(user: User) {
        userReposRepository.setUser(user)
        userReposRepository.loadNextPage()
    }

    fun userError(throwable: Throwable) {
        userReposRepository.clear()
        error.value = throwable
        isLoading.value = false
    }

    override fun onPageLoaded(added: List<Repository>) {
        isLoading.value = false
        loadedRepos.value = added.map {
            val isSubbed = subManager.list().contains(it)
            RepositoryViewData(isSubbed, it)
        }
    }

    override fun onLoadingCompleted() {}

    override fun onError(throwable: Throwable) {
        // Throw upstream
    }

    // Event listeners
    fun pageEndReached() = loadNextPage()

    fun clicked(repo: RepositoryViewData) {
        if (repo.isSubbed) {
            repo.isSubbed = false
            subManager.unsubFrom(repo.repo)
        } else {
            repo.isSubbed = true
            subManager.subscribeTo(repo.repo)
        }
    }


}