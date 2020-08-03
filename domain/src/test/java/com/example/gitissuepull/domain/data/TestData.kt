package com.example.gitissuepull.domain.data

/**
 * Test Data
 */
object TestData {

    val users = listOf(
        User("user1", 1, "avatar/uri1"),
        User("user2", 0, "avatar/uri2"),
        User("user3", 3, "avatar/uri3")
    )

    val repos = listOf(
        Repository(0, "User1Repo1", "", "", users[0]),
        Repository(1, "User3Repo1", "", "", users[2]),
        Repository(2, "User3Repo2", "", "", users[2]),
        Repository(3, "User3Repo3", "", "", users[2])
    )

    // Issue[repoID][issueID]
    val issues = listOf(
        listOf(
            Issue("Repo1 Issue1", "1", users[1]),
            Issue("Repo1 Issue2", "2", users[2])
        ),
        listOf(),
        listOf(
            Issue("Repo3 Issue1", "1", users[0])
        ),
        listOf(
            Issue("Repo4 Issue1", "1", users[0]),
            Issue("Repo4 Issue1", "2", users[1]),
            Issue("Repo4 Issue3", "3", users[2]),
            Issue("Repo4 Issue4", "4", users[0])
        )
    )


}
