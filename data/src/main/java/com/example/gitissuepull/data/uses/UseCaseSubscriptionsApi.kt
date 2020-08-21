package com.example.gitissuepull.data.uses

import com.example.gitissuepull.data.api.SubscriptionsApi
import com.example.gitissuepull.domain.data.Repository
import com.example.gitissuepull.domain.repo.subscriptions.SubscriptionsRepository
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class UseCaseSubscriptionsApiLoad(private val api: SubscriptionsApi): SubscriptionsRepository.UseLoad {
    override fun loadSubscriptions(): Single<List<Repository>> = api.getAll()
}

@Singleton
class UseCaseSubscriptionsApiSave(private val api: SubscriptionsApi): SubscriptionsRepository.UseSave {
    override fun saveSubscriptions(subs: List<Repository>) = api.saveAll(subs)
}