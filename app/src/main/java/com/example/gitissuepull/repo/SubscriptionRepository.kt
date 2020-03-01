package com.example.gitissuepull.repo

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.example.gitissuepull.entity.api.Repository
import com.google.gson.Gson

/// Subscription Manager for repos
class SubscriptionRepository(private val prefs: SharedPreferences) {

    @FunctionalInterface
    interface OnUpdateListener {
        fun subscriptionsUpdated(list: List<Repository>)
    }

    // reuse single instace
    private val gson = Gson()

    // Update listeners
    private val listeners = HashSet<OnUpdateListener>()

    val list = arrayListOf<Repository>()
    val subscriptions = MutableLiveData(list)

    // Deserialize on loading
    init {
        for ((_, str) in prefs.all) if (str is String) {
            list.add(gson.fromJson(str, Repository::class.java))
        }
        subscriptions.value = list // Refresh live data
    }

    // Add listener
    fun addUpdateListener(f: OnUpdateListener) {
        listeners.add(f)
        f.subscriptionsUpdated(list)
    }
    fun removeUpdateListener(f: OnUpdateListener) {
        listeners.remove(f)
    }

    private fun onUpdate() {
        for (v in listeners) v.subscriptionsUpdated(list)
    }

    fun unsubFrom(sub: Repository): Boolean {
        val v = list.remove(sub)
        subscriptions.value = list
        onUpdate()

        // Pop prefs entry
        prefs.edit().remove(sub.id.toString()).apply()

        return v
    }

    /// Will not check if repo actually exist
    fun subscribeTo(repo: Repository) {
        list.add(repo)
        subscriptions.value = list
        onUpdate()

        // Push background prefs update
        prefs.edit().putString(repo.id.toString(), gson.toJson(repo)).apply()
    }

}
