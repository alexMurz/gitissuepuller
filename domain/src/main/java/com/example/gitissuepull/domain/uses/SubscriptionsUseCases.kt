package com.example.gitissuepull.domain.uses

import com.example.gitissuepull.domain.data.Repository
import io.reactivex.Single

/**
 * Load subscriptions for local/shared storage, used once then loading
 */
interface UseCaseLoadSubscriptions {
    fun loadSubscriptions(): Single<List<Repository>>
}

interface UseCaseSaveSubscriptions {
    fun saveSubscriptions(subs: List<Repository>)
}