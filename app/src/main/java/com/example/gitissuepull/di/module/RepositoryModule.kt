package com.example.gitissuepull.di.module

import com.example.gitissuepull.api.CacheSerializerProvider
import com.example.gitissuepull.data.api.IssuesApi
import com.example.gitissuepull.data.api.RepositoryApi
import com.example.gitissuepull.data.api.SubscriptionsApi
import com.example.gitissuepull.data.api.UsersApi
import com.example.gitissuepull.data.uses.*
import com.example.gitissuepull.di.DataScope
import com.example.gitissuepull.domain.data.Issue
import com.example.gitissuepull.domain.repo.issue.BasicIssueRepository
import com.example.gitissuepull.domain.repo.issue.CachedIssueRepository
import com.example.gitissuepull.domain.repo.issue.IssueRepository
import com.example.gitissuepull.domain.repo.subscriptions.BasicSubscriptionsRepository
import com.example.gitissuepull.domain.repo.subscriptions.SubscriptionsRepository
import com.example.gitissuepull.domain.repo.user_repos.BasicUserRepoRepository
import com.example.gitissuepull.domain.repo.user_repos.UserRepoRepository
import com.example.gitissuepull.domain.repo.users.BasicUsersRepository
import com.example.gitissuepull.domain.repo.users.UsersRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    @DataScope
    fun subscriptionRepository(api: SubscriptionsApi): SubscriptionsRepository =
        BasicSubscriptionsRepository(
            UseCaseSubscriptionsApiLoad(api),
            UseCaseSubscriptionsApiSave(api)
        )

    @Provides
    @DataScope
    fun userReposRepository(api: RepositoryApi): UserRepoRepository =
        BasicUserRepoRepository(
            UseCaseUserReposApiGet(api)
        )

    @Provides
    @DataScope
    fun usersRepository(api: UsersApi): UsersRepository =
        BasicUsersRepository(
            UseCaseUsersApiGet(api)
        )

    @Provides
    @DataScope
    fun issuesRepository(api: IssuesApi, provider: CacheSerializerProvider): IssueRepository {
        val serializer = provider.get<String, List<Issue>>("issues")
        return CachedIssueRepository(
            UseCaseUssueApiGetIssues(api),
            serializer,
            serializer
        )
    }

}