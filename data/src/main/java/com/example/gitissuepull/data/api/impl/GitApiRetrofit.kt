package com.example.gitissuepull.data.api.impl

import com.example.gitissuepull.data.Schedulers
import com.example.gitissuepull.data.entries.IssueEntry
import com.example.gitissuepull.data.entries.RepositoryEntry
import com.example.gitissuepull.data.entries.UserEntry
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GitApiRetrofit(private val schedulers: Schedulers): GitApiAccess {
    private companion object {
        private const val URL = "https://api.github.com/"
    }
    private val base = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(schedulers.single()))
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(URL)
        .build()
        .create(GitApiAccess::class.java)

    override fun getIssues(owner: String, repo: String): Single<List<IssueEntry>> =
        base.getIssues(owner, repo).observeOn(schedulers.main())

    override fun getUser(name: String): Single<UserEntry> =
        base.getUser(name).observeOn(schedulers.main())

    override fun getUserReposPage(owner: String, page: Int, perPage: Int): Single<List<RepositoryEntry>> =
        base.getUserReposPage(owner, page, perPage).observeOn(schedulers.main())
}