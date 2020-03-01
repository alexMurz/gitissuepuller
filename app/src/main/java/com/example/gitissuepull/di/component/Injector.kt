package com.example.gitissuepull.di.component

import com.example.gitissuepull.di.scope.InjectorScope
import com.example.gitissuepull.ui.list.ListFragment
import com.example.gitissuepull.ui.list.ListViewModel
import com.example.gitissuepull.ui.main_activity.MainActivity
import com.example.gitissuepull.ui.main_activity.MainActivityViewModel
import com.example.gitissuepull.ui.sub.SubscribeActivity
import com.example.gitissuepull.ui.sub.SubscribeViewModel
import dagger.Component

@InjectorScope
@Component(dependencies = [RepositoryComponent::class])
interface Injector {
    fun inject(v: MainActivity)
    fun inject(v: MainActivityViewModel)

    fun inject(v: ListFragment)
    fun inject(v: ListViewModel)

    fun inject(v: SubscribeActivity)
    fun inject(v: SubscribeViewModel)
}