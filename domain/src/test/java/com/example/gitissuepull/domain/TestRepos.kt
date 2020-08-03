package com.example.gitissuepull.domain

import com.example.gitissuepull.domain.data.Repository
import com.example.gitissuepull.domain.data.TestData
import com.example.gitissuepull.domain.data.TestGetUserRepos
import com.example.gitissuepull.domain.data.User
import com.example.gitissuepull.domain.repo.UserRepoRepository
import org.junit.Assert
import org.junit.Test
import java.lang.NullPointerException

class TestRepos {
    private val repo = UserRepoRepository(TestGetUserRepos())

    @Suppress("ControlFlowWithEmptyBody")
    @Test fun testAll() {
        TestData.users.forEach { user ->
            val repos = TestData.repos.filter { it.owner == user } as ArrayList<Repository>
            val listener = object : UserRepoRepository.Callback {
                override fun onPageLoaded(added: List<Repository>) {
                    for (r in added) assert(repos.remove(r)) {
                        println("Unable to remove $r\nUser: $user")
                    }
                }
                override fun onLoadingCompleted() {  }
                override fun onError(throwable: Throwable) {}
            }
            repo.setUser(user)
            repo.addListener(listener)
            while (repo.loadNextPage());
            Assert.assertEquals(repo.loaded.size, user.publicRepos)
            repo.removeListener(listener)
        }
    }

    @Test(expected = NullPointerException::class) fun fakeUser() {
        repo.setUser(User("1", 1, ""))
        repo.loadNextPage()
    }
}