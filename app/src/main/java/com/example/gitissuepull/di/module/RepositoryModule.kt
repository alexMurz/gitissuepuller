package com.example.gitissuepull.di.module

import android.content.Context
import com.example.gitissuepull.api.GitApiAccess
import com.example.gitissuepull.di.scope.DataScope
import com.example.gitissuepull.repo.*
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule(private val api: GitApiAccess) {

    @Provides
    @DataScope
    fun subscriptionRepository(context: Context) = SubscriptionRepository(
        context.getSharedPreferences("subscriptions", Context.MODE_PRIVATE)
    )

    @Provides
    @DataScope
    fun apiRepository() = api

    @Provides
    @DataScope
    fun issuesRepository(): IIssuesRepository = IssuesRepository(api)

    @Provides
    @DataScope
    fun userReposRepository(): IUserReposRepository = UserReposRepository(api)


}