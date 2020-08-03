package com.example.gitissuepull.ui.main_activity

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitissuepull.domain.data.Repository
import com.example.gitissuepull.domain.repo.SubscriptionsRepository
import javax.inject.Inject

class MainActivityViewModel: ViewModel(), SubscriptionsRepository.Callback {

    @Inject lateinit var subRepo: SubscriptionsRepository

    val subscriptions = MutableLiveData<List<Repository>>()
    val currentSub = MutableLiveData<Repository?>()

    // Validate current sub, and if is not valid put first
    private fun validateCurrent() {
        if (currentSub.value == null || !subRepo.subs.contains(currentSub.value!!)) {
            currentSub.value = subRepo.subs.elementAtOrNull(0)
        }
    }

    override fun subscriptionsUpdated(new: List<Repository>) {
        subscriptions.value = new
        validateCurrent()
    }

    fun attach() {
        subRepo.addListener(this)
    }

    override fun onCleared() {
        super.onCleared()
        subRepo.removeListener(this)
    }

    /// Event Listener
    fun subscriptionClicked(id: Int) {
        val s = subRepo.subs.find { it.id == id }
        // Update only if required
        if (s != currentSub.value) currentSub.value = s
    }

    // Configure item title for drawer
    fun getSubscriptionTitle(sub: Repository) = "${sub.owner.login}/${sub.name}"

    // Request to open current subscription in browser
    // return null to ignore request
    fun openExternally(): Uri? {
        return Uri.parse(currentSub.value?.url ?: return null)
    }

    fun unsubscribe() {
        val sub = currentSub.value ?: return
        val idx = subRepo.subs.indexOf(sub)
        subRepo.unsubFrom(sub)
        // Show previous sub
        currentSub.value = subRepo.subs.elementAtOrNull(idx-1)
    }
}