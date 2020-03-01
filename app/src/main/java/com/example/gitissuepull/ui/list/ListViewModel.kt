package com.example.gitissuepull.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitissuepull.entity.RepositoryResult
import com.example.gitissuepull.entity.api.Issue
import com.example.gitissuepull.repo.IIssuesRepository
import javax.inject.Inject

class ListViewModel : ViewModel(), IIssuesRepository.Listener {

    @Inject
    lateinit var repo: IIssuesRepository

    val isLoading = MutableLiveData<Boolean>()
    val issueData = MutableLiveData<RepositoryResult<List<Issue>>>()

    // Repo data, given by fragment on init
    fun attachWith(owner: String, repo: String) {
        this.repo.addListener(this)
        this.repo.setSource(owner, repo)
    }

    override fun onCleared() {
        super.onCleared()
        repo.removeListener(this)
    }

    override fun onSuccess(l: List<Issue>) {
        isLoading.value = false
        issueData.value = RepositoryResult.fromValue(l)
    }

    override fun onError(l: Throwable) {
        isLoading.value = false
        issueData.value = RepositoryResult.fromError(l)
    }

    // Event Listener for View
    fun onRefresh() = repo.reloadData()

}
