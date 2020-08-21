package com.example.gitissuepull.domain.repo.users

import com.example.gitissuepull.domain.data.User
import io.reactivex.Single

interface UsersRepository {
    fun getUser(name: String): Single<User>

    // Use
    interface UseGet {
        fun getUser(name: String): Single<User>
    }
}