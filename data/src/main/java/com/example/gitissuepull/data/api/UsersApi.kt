package com.example.gitissuepull.data.api

import com.example.gitissuepull.data.entries.UserEntry
import io.reactivex.Single

interface UsersApi {
    fun getUser(name: String): Single<UserEntry>
}