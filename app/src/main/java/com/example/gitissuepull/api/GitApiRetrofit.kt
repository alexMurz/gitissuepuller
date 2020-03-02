package com.example.gitissuepull.api

import com.example.gitissuepull.entity.api.Issue
import com.example.gitissuepull.entity.api.Repository
import com.example.gitissuepull.entity.api.User
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GitApiRetrofit: GitApiAccess {
    private companion object {
        private const val URL = "https://api.github.com/"
    }
    private val base = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.single()))
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(URL)
        .build()
        .create(GitApiAccess::class.java)

    override fun getIssues(owner: String, repo: String): Single<List<Issue>> =
        base.getIssues(owner, repo).observeOn(AndroidSchedulers.mainThread())

    override fun getIssuesSince(owner: String, repo: String, date: String): Single<List<Issue>> =
        base.getIssuesSince(owner, repo, date).observeOn(AndroidSchedulers.mainThread())

    override fun getUser(owner: String): Single<User> =
        base.getUser(owner).observeOn(AndroidSchedulers.mainThread())

    override fun getUserRepos(owner: String, page: Int, per_page: Int): Single<List<Repository>> =
        base.getUserRepos(owner, page, per_page).observeOn(AndroidSchedulers.mainThread())

    override fun getRepoByID(id: String): Single<Repository> =
        base.getRepoByID(id).observeOn(AndroidSchedulers.mainThread())


}