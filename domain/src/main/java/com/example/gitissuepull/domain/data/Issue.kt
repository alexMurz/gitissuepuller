package com.example.gitissuepull.domain.data

data class Issue(
    var title: String,
    var createdAt: String,
    var user: User
)