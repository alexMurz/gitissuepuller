package com.example.gitissuepull.data.api.impl

import com.example.gitissuepull.data.api.IssuesApi
import com.example.gitissuepull.data.api.RepositoryApi
import com.example.gitissuepull.data.api.UsersApi
import com.example.gitissuepull.data.entries.IssueEntry
import com.example.gitissuepull.data.entries.RepositoryEntry
import com.example.gitissuepull.data.entries.UserEntry
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitApiAccess: IssuesApi, UsersApi, RepositoryApi {

    @GET("/repos/{owner}/{repo}/issues")
    override fun getIssues(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Single<List<IssueEntry>>

    @GET("/users/{user}/repos")
    override fun getUserReposPage(
        @Path("user") owner: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<List<RepositoryEntry>>

    @GET("/users/{user}")
    override fun getUser(
        @Path("user") name: String
    ): Single<UserEntry>

}