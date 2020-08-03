package com.example.gitissuepull

import io.reactivex.schedulers.Schedulers as RxSchedulers
import io.reactivex.android.schedulers.AndroidSchedulers as RxAndroidSchedulers
import com.example.gitissuepull.data.Schedulers
import io.reactivex.Scheduler

object AndroidSchedulers: Schedulers {
    override fun io() = RxSchedulers.io()
    override fun single() = RxSchedulers.single()
    override fun main(): Scheduler = RxAndroidSchedulers.mainThread()
}