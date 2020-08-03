package com.example.gitissuepull.data.uses

import com.example.gitissuepull.data.api.UsersApi
import com.example.gitissuepull.data.entries.UserEntryMapper
import com.example.gitissuepull.domain.data.User
import com.example.gitissuepull.domain.uses.UseCaseGetUser
import io.reactivex.Single
import javax.inject.Singleton


@Singleton
class UseCaseUsersApiGet(private val api: UsersApi): UseCaseGetUser {
    override fun getUser(name: String): Single<User> = api.getUser(name).map { UserEntryMapper(it) }
}