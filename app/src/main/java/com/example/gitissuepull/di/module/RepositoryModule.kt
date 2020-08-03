package com.example.gitissuepull.di.module

import com.example.gitissuepull.data.api.IssuesApi
import com.example.gitissuepull.data.api.RepositoryApi
import com.example.gitissuepull.data.api.SubscriptionsApi
import com.example.gitissuepull.data.api.UsersApi
import com.example.gitissuepull.data.uses.*
import com.example.gitissuepull.di.DataScope
import com.example.gitissuepull.domain.repo.IssueRepository
import com.example.gitissuepull.domain.repo.SubscriptionsRepository
import com.example.gitissuepull.domain.repo.UserRepoRepository
import com.example.gitissuepull.domain.repo.UsersRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    @DataScope
    fun subscriptionRepository(api: SubscriptionsApi) = SubscriptionsRepository(
        UseCaseSubscriptionsApiLoad(api),
        UseCaseSubscriptionsApiSave(api)
    )

    @Provides
    @DataScope
    fun userReposRepository(api: RepositoryApi) = UserRepoRepository(UseCaseUserReposApiGet(api))

    @Provides
    @DataScope
    fun usersRepository(api: UsersApi) = UsersRepository(UseCaseUsersApiGet(api))

    @Provides
    @DataScope
    fun issuesRepository(api: IssuesApi) = IssueRepository(UseCaseUssueApiGetIssues(api))

}