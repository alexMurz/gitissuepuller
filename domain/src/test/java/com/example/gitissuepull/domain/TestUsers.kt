package com.example.gitissuepull.domain

import com.example.gitissuepull.domain.data.TestData
import com.example.gitissuepull.domain.data.TestGetUsers
import com.example.gitissuepull.domain.repo.users.BasicUsersRepository
import org.junit.Test
import org.junit.Assert.*


class TestUsers {
    private val usersRepo =
        BasicUsersRepository(TestGetUsers())

    @Test fun allUsersForName() {
        TestData.users.forEachIndexed { index, u1 ->
            usersRepo.getUser(u1.login).subscribe { it ->
                assertEquals(it, TestData.users[index])
            }
        }
    }

    @Test(expected = NullPointerException::class) fun nonexistent() {
        usersRepo.getUser("Nonexistent").subscribe()
    }
}