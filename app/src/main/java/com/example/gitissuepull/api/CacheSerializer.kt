package com.example.gitissuepull.api

import com.example.gitissuepull.domain.cache.TimedCacheSerializer
import io.paperdb.Book
import io.reactivex.Scheduler

class CacheSerializerProvider(val book: Book, val schedulers: Scheduler) {
    fun <K, V> get(tag: String): CacheSerializer<K, V> {
        return CacheSerializer(book, tag, schedulers)
    }
}

// Local cache storage, using paper
class CacheSerializer<K, T>(val book: Book, val tag: String, val scheduler: Scheduler) :
    TimedCacheSerializer.Serializer<K, T>,
    TimedCacheSerializer.Deserializer<K, T>
{
    /**
     * Async serialization
     */
    override fun serialize(map: List<Pair<K, T>>) {
//        scheduler.scheduleDirect {
            book.write(tag, map)
//        }
    }
    override fun deserialize(): List<Pair<K, T>> = book.read(tag, emptyList())
}