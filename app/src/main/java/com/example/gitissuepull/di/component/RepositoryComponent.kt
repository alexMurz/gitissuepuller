package com.example.gitissuepull.di.component

import android.content.Context
import com.example.gitissuepull.data.api.IssuesApi
import com.example.gitissuepull.data.api.RepositoryApi
import com.example.gitissuepull.data.api.SubscriptionsApi
import com.example.gitissuepull.data.api.UsersApi
import com.example.gitissuepull.di.module.RepositoryModule
import com.example.gitissuepull.di.DataScope
import com.example.gitissuepull.domain.repo.issue.IssueRepository
import com.example.gitissuepull.domain.repo.subscriptions.SubscriptionsRepository
import com.example.gitissuepull.domain.repo.user_repos.UserRepoRepository
import com.example.gitissuepull.domain.repo.users.UsersRepository

import dagger.Component


@DataScope
@Component(dependencies = [AppComponent::class], modules = [RepositoryModule::class])
interface RepositoryComponent {
    // Api deps
    fun context(): Context
    fun issues(): IssuesApi
    fun repository(): RepositoryApi
    fun users(): UsersApi
    fun subscriptions(): SubscriptionsApi

    fun subscriptionsRepository(): SubscriptionsRepository
    fun userReposRepository(): UserRepoRepository
    fun usersRepository(): UsersRepository
    fun issuesRepository(): IssueRepository
}