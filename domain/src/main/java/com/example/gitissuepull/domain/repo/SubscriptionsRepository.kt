package com.example.gitissuepull.domain.repo

import com.example.gitissuepull.domain.data.Repository
import com.example.gitissuepull.domain.uses.UseCaseLoadSubscriptions
import com.example.gitissuepull.domain.uses.UseCaseSaveSubscriptions
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class SubscriptionsRepository(
    private val useLoad: UseCaseLoadSubscriptions,
    private val useSave: UseCaseSaveSubscriptions
): RepositoryBase<SubscriptionsRepository.Callback>() {

    // Subs list
    val subs = ArrayList<Repository>()
    init {
        useLoad.loadSubscriptions().subscribe(object : SingleObserver<List<Repository>> {
            var d: Disposable? = null
            override fun onSubscribe(d: Disposable) {
                this.d = d
            }
            override fun onSuccess(t: List<Repository>) {
                subs.addAll(t)
                d?.dispose()
            }
            override fun onError(e: Throwable) {
                e.printStackTrace()
            }
        })
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