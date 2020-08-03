package com.example.gitissuepull.ui.sub

import com.example.gitissuepull.domain.data.Repository

/**
 * Extra isSubbed field for visual then searching for new subs
 */
data class RepositoryViewData(
    var isSubbed: Boolean,
    val repo: Repository
)