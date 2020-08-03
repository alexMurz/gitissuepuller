package com.example.gitissuepull

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.example.gitissuepull.di.component.*
import com.example.gitissuepull.di.module.AppModule
import com.example.gitissuepull.di.module.RepositoryModule

class App: Application() {
    companion object {
        fun get(ctx: Activity) = ctx.application as App
        fun get(ctx: Fragment) = get(ctx.requireActivity())

        fun injector(ctx: Activity) = get(ctx).injector
        fun injector(ctx: Fragment) = get(ctx).injector
    }

    lateinit var appComponent: AppComponent; private set
    lateinit var repositoryComponent: RepositoryComponent; private set
    lateinit var injector: Injector; private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        repositoryComponent = DaggerRepositoryComponent.builder()
            .appComponent(appComponent)
            .build()

        injector = DaggerInjector.builder()
            .repositoryComponent(repositoryComponent)
            .build()
    }


}