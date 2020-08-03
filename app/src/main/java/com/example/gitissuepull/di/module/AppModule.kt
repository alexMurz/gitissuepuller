package com.example.gitissuepull.di.module

import android.content.Context
import com.example.gitissuepull.AndroidSchedulers
import com.example.gitissuepull.data.Schedulers
import com.example.gitissuepull.data.api.SubscriptionsApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(
    val context: Context
) {
    @Provides @Singleton fun context(): Context = context
    @Provides @Singleton fun schedulers(): Schedulers = AndroidSchedulers
}