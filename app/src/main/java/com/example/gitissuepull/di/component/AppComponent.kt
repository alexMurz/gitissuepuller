package com.example.gitissuepull.di.component

import android.content.Context
import com.example.gitissuepull.data.Schedulers
import com.example.gitissuepull.data.api.IssuesApi
import com.example.gitissuepull.data.api.RepositoryApi
import com.example.gitissuepull.data.api.SubscriptionsApi
import com.example.gitissuepull.data.api.UsersApi
import com.example.gitissuepull.di.module.ApiModule
import com.example.gitissuepull.di.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface AppComponent {
    // App
    fun context(): Context
    fun schedulers(): Schedulers

    // Api
    fun issues(): IssuesApi
    fun repository(): RepositoryApi
    fun users(): UsersApi
    fun subscriptions(): SubscriptionsApi
}