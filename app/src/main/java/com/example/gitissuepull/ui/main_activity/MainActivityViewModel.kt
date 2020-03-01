package com.example.gitissuepull.ui.main_activity

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitissuepull.repo.SubscriptionRepository
import com.example.gitissuepull.entity.api.Repository
import javax.inject.Inject

class MainActivityViewModel: ViewModel(), SubscriptionRepository.OnUpdateListener {

    @Inject
    lateinit var subManager: SubscriptionRepository

    val subscriptions = MutableLiveData<List<Repository>>()
    val currentSub = MutableLiveData<Repository?>()

    // Check if current sub still in subscriptions
    // Then `null` try put any sub if repository have some to offer
    private fun validateSub() {
        if (currentSub.value == null || !subManager.list.contains(currentSub.value!!)) {
            currentSub.value = subManager.list.elementAtOrNull(0)
        }
    }

    fun attach() {
        subManager.addUpdateListener(this)
        validateSub()
    }

    override fun onCleared() {
        super.onCleared()
        subManager.removeUpdateListener(this)
    }

    /// Repository Listener
    override fun subscriptionsUpdated(list: List<Repository>) {
        subscriptions.value = list
        validateSub()
    }

    /// Event Listener
    fun subscriptionClicked(id: Int) {
        val s = subManager.list.find { it.id == id }
        // Update only if required
        if (s != currentSub.value) currentSub.value = s
    }

    // Configure item title for drawer
    fun getSubscriptionTitle(sub: Repository) = "${sub.owner.login}/${sub.name}"

    // Request to open current subscription in browser
    // return null to ignore request
    fun openExternally(): Uri? {
        return Uri.parse(currentSub.value?.htmlUrl ?: return null)
    }

    fun unsubscribe() {
        val sub = currentSub.value ?: return
        val idx = subManager.list.indexOf(sub)
        subManager.unsubFrom(sub)
        // Show previous sub
        currentSub.value = subManager.list.elementAtOrNull(idx-1)
    }
    

}