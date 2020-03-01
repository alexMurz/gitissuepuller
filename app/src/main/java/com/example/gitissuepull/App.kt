package com.example.gitissuepull

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.example.gitissuepull.api.GitApiAccess
import com.example.gitissuepull.api.GitApiRetrofit
import com.example.gitissuepull.di.component.*
import com.example.gitissuepull.di.module.AppModule
import com.example.gitissuepull.di.module.RepositoryModule

class App: Application() {
    companion object {
        fun injector(ctx: Activity) = (ctx.application as App).injector
        fun injector(ctx: Fragment) = injector(ctx.activity!!)
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
            .repositoryModule(RepositoryModule(GitApiRetrofit()))
            .build()

        injector = DaggerInjector.builder()
            .repositoryComponent(repositoryComponent)
            .build()

        // Start foreground notification service
//        val intent = Intent(this, NotificationService::class.java)
//        ContextCompat.startForegroundService(this, intent)

    }

}