package com.example.gitissuepull.domain.repo

import com.example.gitissuepull.domain.data.Repository
import com.example.gitissuepull.domain.uses.UseCaseLoadSubscriptions
import com.example.gitissuepull.domain.uses.UseCaseSaveSubscriptions

class SubscriptionsRepository(
    private val useLoad: UseCaseLoadSubscriptions,
    private val useSave: UseCaseSaveSubscriptions
): RepositoryBase<SubscriptionsRepository.Callback>() {

    // Subs list
    val subs = ArrayList<Repository>()
    init {
        useLoad.loadSubscriptions().subscribe(
            { it -> subs.addAll(it) },
            { err -> }
        )
        useSave.saveSubscriptions(subs)
    }

    override fun onListenerAdded(l: Callback) = l.subscriptionsUpdated(subs)

    fun subscribeTo(sub: Repository) {
        subs.add(sub)
        useSave.saveSubscriptions(subs)
        callback { subscriptionsUpdated(subs) }
    }

    fun unsubFrom(sub: Repository): Boolean {
        val r = subs.remove(sub)
        useSave.saveSubscriptions(subs)
        return r
    }

    interface Callback {
        fun subscriptionsUpdated(new: List<Repository>)
    }
}