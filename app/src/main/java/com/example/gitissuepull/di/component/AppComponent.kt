package com.example.gitissuepull.di.component

import android.content.Context
import com.example.gitissuepull.di.module.AppModule
import com.example.gitissuepull.ui.main_activity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun context(): Context
}