package com.example.gitissuepull.domain.repo.subscriptions

import com.example.gitissuepull.domain.data.Repository
import com.example.gitissuepull.domain.repo.RepositoryBase
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class BasicSubscriptionsRepository(
    useLoad: SubscriptionsRepository.UseLoad,
    private val useSave: SubscriptionsRepository.UseSave
): SubscriptionsRepository, RepositoryBase<SubscriptionsRepository.Callback>() {

    // Subs list
    val subs = ArrayList<Repository>()
    override fun list() = subs

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

    override fun onListenerAdded(l: SubscriptionsRepository.Callback) = l.subscriptionsUpdated(subs)

    override fun subscribeTo(sub: Repository) {
        subs.add(sub)
        useSave.saveSubscriptions(subs)
        callback { subscriptionsUpdated(subs) }
    }

    override fun unsubFrom(sub: Repository): Boolean {
        val r = subs.remove(sub)
        useSave.saveSubscriptions(subs)
        return r
    }

}

