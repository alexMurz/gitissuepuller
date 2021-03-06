package com.example.gitissuepull.di.module

import com.example.gitissuepull.api.CacheSerializerProvider
import com.example.gitissuepull.api.SubscriptionsApiPaper
import com.example.gitissuepull.data.Schedulers
import com.example.gitissuepull.data.api.IssuesApi
import com.example.gitissuepull.data.api.RepositoryApi
import com.example.gitissuepull.data.api.SubscriptionsApi
import com.example.gitissuepull.data.api.UsersApi
import com.example.gitissuepull.data.api.impl.GitApiAccess
import com.example.gitissuepull.data.api.impl.GitApiRetrofit
import dagger.Module
import dagger.Provides
import io.paperdb.Paper
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides @Singleton fun retrofitApi(s: Schedulers): GitApiAccess = GitApiRetrofit(s)

    @Provides @Singleton fun issues(s: Schedulers): IssuesApi = retrofitApi(s)
    @Provides @Singleton fun repository(s: Schedulers): RepositoryApi = retrofitApi(s)
    @Provides @Singleton fun users(s: Schedulers): UsersApi = retrofitApi(s)

    @Provides @Singleton fun subscriptions(s: Schedulers): SubscriptionsApi {
        return SubscriptionsApiPaper(Paper.book(), s.single())
    }

    @Provides @Singleton fun cacheSerializerProvider(s: Schedulers): CacheSerializerProvider {
        return CacheSerializerProvider(Paper.book("cache"), s.single())
    }

}