package com.example.gitissuepull.domain.uses

import com.example.gitissuepull.domain.data.User
import io.reactivex.Single

interface UseCaseGetUser {
    fun getUser(name: String): Single<User>
}