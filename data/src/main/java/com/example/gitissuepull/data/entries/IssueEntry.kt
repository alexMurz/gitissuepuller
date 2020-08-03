package com.example.gitissuepull.data.entries

import com.example.gitissuepull.domain.data.Issue
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class IssueEntry(
    @SerializedName("title")
    @Expose
    var title: String,
    @SerializedName("created_at")
    @Expose
    var createdAt: String,
    @SerializedName("user")
    @Expose
    var user: UserEntry
)

object IssueEntryMapper: (IssueEntry) -> Issue {
    override fun invoke(i: IssueEntry) = Issue(
        i.title,
        i.createdAt,
        UserEntryMapper(i.user)
    )
}
