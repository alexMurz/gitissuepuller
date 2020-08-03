package com.example.gitissuepull.domain.data

data class Repository(
    var id: Int,
    var name: String,
    var updatedAt: String,
    var url: String,
    var owner: User
) {
    val fullName: String; get() = name
}
