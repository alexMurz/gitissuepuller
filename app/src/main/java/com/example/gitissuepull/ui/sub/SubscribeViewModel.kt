package com.example.gitissuepull.ui.sub

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitissuepull.entity.RepositoryResult
import com.example.gitissuepull.repo.SubscriptionRepository
import com.example.gitissuepull.entity.api.Repository
import com.example.gitissuepull.entity.api.User
import com.example.gitissuepull.repo.IUserReposRepository
import javax.inject.Inject

class SubscribeViewModel: ViewModel(), IUserReposRepository.Listener {

    @Inject
    lateinit var subManager: SubscriptionRepository
    @Inject
    lateinit var userRepos: IUserReposRepository

    val isLoading = MutableLiveData(false)
    val loadedRepos = MutableLiveData<RepositoryResult<List<Repository>>>()

    fun attach() { userRepos.addListener(this) }

    override fun onCleared() {
        super.onCleared()
        userRepos.removeListener(this)
    }

    fun startLoading(owner: String) {
        loadedRepos.value = null
        isLoading.value = true
        userRepos.setCurrentUser(owner)
    }

    private fun loadNextPage() {
        // Await previous task, whatever that is
        if (isLoading.value == true) return
        if (userRepos.loadNextPage())
            isLoading.value = true

    }

    // UserReposRepositoryListener
    override fun onUserInfoLoaded(user: User) {
        userRepos.loadNextPage()
    }

    override fun onPageLoaded(page: List<Repository>) {
        isLoading.value = false
        loadedRepos.value = RepositoryResult.fromValue(userRepos.loadedRepositories)
    }

    override fun onLoadingCompleted() {}

    override fun onError(e: Throwable) {
        loadedRepos.value = RepositoryResult.fromError(e)
    }

    // Event listeners
    fun pageEndReached() = loadNextPage()

    fun clicked(repo: Repository) {
        if (repo.subCheck) {
            repo.subCheck = false
            subManager.unsubFrom(repo)
        } else {
            repo.subCheck = true
            subManager.subscribeTo(repo)
        }
    }


}