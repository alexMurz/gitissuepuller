package com.example.gitissuepull.domain.cache

/**
 * Serialize util for TimedCache
 */
class TimedCacheSerializer<K, T>(
    private val serializer: Serializer<K, T>,
    private val deserializer: Deserializer<K, T>
)
{
    interface Serializer<K, T> {
        /**
         * Serialize list of <Key,Item>'s
         */
        fun serialize(map: List<Pair<K, T>>)
    }
    interface Deserializer<K, T> {
        /**
         * Deserialize as list of <Key,Item>'s
         */
        fun deserialize(): List<Pair<K, T>>
    }

    /**
     * Insert all cache items into Serializer
     */
    fun serialize(cache: TimedCache<K, T>) {
        val v = cache.map.map { it.key to it.value.item }
        println("TT123 Save ${v.joinToString()}")
        serializer.serialize(v)
    }

    /**
     * Get all items from Deserializer, clear cache and insert new ones as old
     */
    fun deserialize(cache: TimedCache<K, T>) {
        val map = deserializer.deserialize()
            .map {
                println("TT123 Read ${it.first} ${it.second}")
                Pair(it.first, TimedCache.Container(0, it.second))
            }
            .toMap()
        cache.map.clear()
        cache.map.putAll(map)
    }

}