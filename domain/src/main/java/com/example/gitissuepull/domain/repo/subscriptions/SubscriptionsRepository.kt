package com.example.gitissuepull.domain.repo.subscriptions

import com.example.gitissuepull.domain.data.Repository
import com.example.gitissuepull.domain.repo.IRepositoryBase
import io.reactivex.Single

interface SubscriptionsRepository: IRepositoryBase<SubscriptionsRepository.Callback> {

    fun list(): List<Repository>
    fun subscribeTo(sub: Repository)
    fun unsubFrom(sub: Repository): Boolean

    // Uses
    interface UseLoad {
        fun loadSubscriptions(): Single<List<Repository>>
    }

    interface UseSave {
        fun saveSubscriptions(subs: List<Repository>)
    }

    // Callback
    interface Callback {
        fun subscriptionsUpdated(new: List<Repository>)
    }
}
