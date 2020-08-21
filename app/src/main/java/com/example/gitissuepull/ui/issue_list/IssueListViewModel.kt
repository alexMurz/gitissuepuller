package com.example.gitissuepull.ui.issue_list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitissuepull.domain.data.Issue
import com.example.gitissuepull.domain.repo.issue.BasicIssueRepository
import com.example.gitissuepull.domain.repo.issue.IssueRepository
import javax.inject.Inject

class IssueListViewModel : ViewModel(),
    IssueRepository.Result {

    @Inject
    lateinit var repo: IssueRepository

    val isLoading = MutableLiveData<Boolean>()
    val issueData = MutableLiveData<List<Issue>>()
    val error = MutableLiveData<Throwable>()


    // Repo data, given by fragment on init
    private var owner: String? = null
    private var ownerRepo: String? = null
    fun attachWith(owner: String, repo: String) {
        this.owner = owner
        this.ownerRepo = repo
        this.repo.addListener(this)
        this.repo.get(owner, repo)
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
        this.error.value = error
        Log.e("IssueListViewModel", "Error: $error")
    }

    // Event Listener for View
    fun onRefresh() {
        val owner = owner ?: return
        val ownerRepo = ownerRepo ?: return
        repo.load(owner, ownerRepo)
    }

}
