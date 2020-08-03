package com.example.gitissuepull.data

import io.reactivex.Scheduler
import javax.inject.Singleton

/**
 * Schedulers provider for API access points, impl by presenter
 */
@Singleton interface Schedulers {
    fun io(): Scheduler
    fun single(): Scheduler
    fun main(): Scheduler
}