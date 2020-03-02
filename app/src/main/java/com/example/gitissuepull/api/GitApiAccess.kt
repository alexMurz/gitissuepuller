package com.example.gitissuepull.api

import com.example.gitissuepull.entity.api.Issue
import com.example.gitissuepull.entity.api.Repository
import com.example.gitissuepull.entity.api.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitApiAccess {


    @GET("/repos/{owner}/{repo}/issues")
    fun getIssues(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Single<List<Issue>>

    // ISO 8601 YYYY-MM-DDTHH:MM:SSZ
    @GET("/repos/{owner}/{repo}/issues")
    fun getIssuesSince(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("since") date: String
    ): Single<List<Issue>>

    @GET("/users/{user}/repos")
    fun getUserRepos(
        @Path("user") owner: String,
        @Query("page") page: Int = 0,
        @Query("per_page") per_page: Int = 10
    ): Single<List<Repository>>

    @GET("/users/{user}")
    fun getUser(
        @Path("user") owner: String
    ): Single<User>

    @GET("/repositories/{id}")
    fun getRepoByID(
        @Path("id") id: String
    ): Single<Repository>

}