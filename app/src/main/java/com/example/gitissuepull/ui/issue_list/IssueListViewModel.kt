package com.example.gitissuepull.ui.issue_list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitissuepull.domain.data.Issue
import com.example.gitissuepull.domain.repo.IssueRepository
import javax.inject.Inject

class IssueListViewModel : ViewModel(), IssueRepository.Result {

    @Inject
    lateinit var repo: IssueRepository

    val isLoading = MutableLiveData<Boolean>()
    val issueData = MutableLiveData<List<Issue>>()

    // Repo data, given by fragment on init
    fun attachWith(owner: String, repo: String) {
        this.repo.addListener(this)
        this.repo.setSource(owner, repo)
    }

    override fun onCleared() {
        super.onCleared()
        repo.removeListener(this)
    }

    override fun onSuccess(list: List<Issue>) {
        isLoading.value = false
        issueData.value = list
    }

    override fun onError(error: Throwable) {
        isLoading.value = false
        Log.e("IssueListViewModel", "Error: $error")
    }

    // Event Listener for View
    fun onRefresh() = repo.reloadData()

}