package com.example.gitissuepull.domain.repo

import com.example.gitissuepull.domain.uses.UseCaseGetUser

class UsersRepository(
    val get: UseCaseGetUser
) {
    fun getUser(name: String) = get.getUser(name)
}