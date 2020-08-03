package com.example.gitissuepull.domain.uses

import com.example.gitissuepull.domain.data.Repository
import io.reactivex.Single

interface UseCaseGetUserReposPage {
    fun getUserReposPage(owner: String, page: Int, perPage: Int): Single<List<Repository>>
}