package com.example.gitissuepull.data.entries

import com.example.gitissuepull.domain.data.User
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserEntry(
    @SerializedName("login")
    @Expose var login: String,
    @SerializedName("public_repos")
    @Expose var publicRepos: Int,
    @SerializedName("avatar_url")
    @Expose var avatarUrl: String
)

object UserEntryMapper: (UserEntry) -> User {
    override fun invoke(i: UserEntry) = User(
        i.login,
        i.publicRepos,
        i.avatarUrl
    )
}