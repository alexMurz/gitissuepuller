package com.example.gitissuepull.domain.repo.users

class BasicUsersRepository(val get: UsersRepository.UseGet): UsersRepository {
    override fun getUser(name: String) = get.getUser(name)
}