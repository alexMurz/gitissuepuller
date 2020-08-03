package com.example.gitissuepull.di.component

import com.example.gitissuepull.di.InjectorScope
import com.example.gitissuepull.ui.issue_list.IssueListFragment
import com.example.gitissuepull.ui.issue_list.IssueListViewModel
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

    fun inject(v: IssueListFragment)
    fun inject(v: IssueListViewModel)

    fun inject(v: SubscribeActivity)
    fun inject(v: SubscribeViewModel)

}