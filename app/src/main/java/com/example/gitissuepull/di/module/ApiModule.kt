package com.example.gitissuepull.di.module

import com.example.gitissuepull.api.GitApiAccess
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule(private val api: GitApiAccess) {
    @Provides
    @Singleton
    fun provideGitApi() = api
}