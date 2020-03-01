package com.example.gitissuepull.di.component

import android.content.Context
import com.example.gitissuepull.di.module.RepositoryModule
import com.example.gitissuepull.di.scope.DataScope
import com.example.gitissuepull.repo.IIssuesRepository
import com.example.gitissuepull.repo.IUserReposRepository
import com.example.gitissuepull.repo.SubscriptionRepository
import dagger.Component


@DataScope
@Component(dependencies = [AppComponent::class], modules = [RepositoryModule::class])
interface RepositoryComponent {
    // Deps
    fun context(): Context

    fun subscriptionsRepository(): SubscriptionRepository
    fun issuesRepository(): IIssuesRepository
    fun userReposRepository(): IUserReposRepository
}