package com.example.gitissuepull.data.api

import com.example.gitissuepull.domain.data.Repository
import io.reactivex.Single

// For platform specific overrides
interface SubscriptionsApi {
    fun getAll(): Single<List<Repository>>
    fun saveAll(list: List<Repository>)
}