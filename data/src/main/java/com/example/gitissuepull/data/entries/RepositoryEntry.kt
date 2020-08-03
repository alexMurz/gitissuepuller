package com.example.gitissuepull.data.entries

import com.example.gitissuepull.domain.data.Repository
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RepositoryEntry(
    @SerializedName("id")
    @Expose
    var id: Int,

    @SerializedName("name")
    @Expose
    var name: String,

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String,

    @SerializedName("html_url")
    @Expose
    var url: String,

    @SerializedName("owner")
    @Expose
    var owner: UserEntry
)

object RepositoryEntryMapper: (RepositoryEntry) -> Repository {
    override fun invoke(i: RepositoryEntry) = Repository(
        i.id,
        i.name,
        i.updatedAt,
        i.url,
        UserEntryMapper(i.owner)
    )
}
