package com.example.gitissuepull.data.uses

import com.example.gitissuepull.data.api.SubscriptionsApi
import com.example.gitissuepull.domain.data.Repository
import com.example.gitissuepull.domain.uses.UseCaseLoadSubscriptions
import com.example.gitissuepull.domain.uses.UseCaseSaveSubscriptions
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class UseCaseSubscriptionsApiLoad(private val api: SubscriptionsApi): UseCaseLoadSubscriptions {
    override fun loadSubscriptions(): Single<List<Repository>> = api.getAll()
}

@Singleton
class UseCaseSubscriptionsApiSave(private val api: SubscriptionsApi): UseCaseSaveSubscriptions {
    override fun saveSubscriptions(subs: List<Repository>) = api.saveAll(subs)
}