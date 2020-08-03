package com.example.gitissuepull.api

import com.example.gitissuepull.data.api.SubscriptionsApi
import com.example.gitissuepull.domain.data.Repository
import com.example.gitissuepull.domain.data.User
import io.paperdb.Book
import io.reactivex.Scheduler
import io.reactivex.Single

// Local storage for subscriptions
class SubscriptionsApiPaper(val book: Book, val scheduler: Scheduler): SubscriptionsApi {
    companion object {
        private const val book_tag = "subscriptions"
    }

    override fun getAll(): Single<List<Repository>> {
        val list = book.read<ArrayList<Repository>>(book_tag, ArrayList())
//        list.add(Repository(
//            0,
//            "RxPaper2",
//            "0",
//            "URL",
//            User(
//                "pakoito",
//                "AVATAR_URL"
//            )
//        ))
        println("PAPER LOADED: ${list.joinToString()}")
        return Single.just(list)
    }

    override fun saveAll(list: List<Repository>) {
        println("Save list: ${list.joinToString()}")
        book.write(book_tag, list)
    }

}