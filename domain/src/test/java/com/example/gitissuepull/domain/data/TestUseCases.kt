package com.example.gitissuepull.domain.data
import com.example.gitissuepull.domain.repo.issue.IssueRepository
import com.example.gitissuepull.domain.repo.user_repos.UserRepoRepository
import com.example.gitissuepull.domain.repo.users.UsersRepository
import io.reactivex.Single

class TestGetUsers: UsersRepository.UseGet {
    override fun getUser(name: String): Single<User> =
        Single.just(TestData.users.find { it.login.equals(name)} )
}

class TestGetUserRepos: UserRepoRepository.UseGet {
    override fun getUserReposPage(
        owner: String,
        page: Int,
        perPage: Int
    ): Single<List<Repository>> =
        Single.just(run {
            val user = TestData.users.find { it.login == owner } ?: return@run null
            TestData.repos.filter { it.owner == user }
        })
}

class TestGetIssues: IssueRepository.UseGet {
    override fun getIssues(user: String, repository: String): Single<List<Issue>> =
        Single.just(run {
            val u1 = TestData.users.find { it.login == user } ?: return@run null
            val repoID = TestData.repos.indexOfFirst { it.owner == u1 && it.name == repository }
            TestData.issues.getOrNull(repoID)
        })

}
