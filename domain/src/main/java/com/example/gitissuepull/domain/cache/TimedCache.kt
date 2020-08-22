package com.example.gitissuepull.domain.cache

/**
 * Cache with time invalidated items
 * Item themselves are not removed from backed map
 */
class TimedCache<K, T>(var timeout: Long) {

    internal data class Container<T>(val spawn: Long, val item: T)

    internal val map = HashMap<K, Container<T>>()

    /**
     * Return `Long` timestamp
     */
    private fun time() = System.currentTimeMillis()

    /**
     * Get Item for key, without timeout test
     */
    fun rawGet(key: K): T? = map[key]?.item

    /**
     * Get item for key, or null
     */
    fun get(key: K): T? = map[key]?.let {
        if (it.spawn + timeout >= time()) {
            return it.item
        } else {
            return null
        }
    }

    /**
     * Force insert/replace item
     */
    fun insert(key: K, item: T): T = item.apply { map[key] = Container(time(), item) }

    /**
     * Force insert/replace already dead item, that wishes to be updated a soon as possible
     */
    fun insertOld(key: K, item: T): T = item.apply { map[key] = Container(0, item) }

    /**
     * Get item, or insert with `itemFun`
     * Will update then existing item invalidated
     */
    fun update(key: K, itemFun: () -> T): T = get(key) ?: insert(key, itemFun())

    /**
     * Get item, or update with `itemFun` when it yields not null
     * Will update then existing item invalidated, if `itemFun` yield null,
     *  return old item
     */
    fun tryUpdate(key: K, itemFun: () -> T?): T? {
        // Get old
        return get(key) ?: run {
            // Or try update
            itemFun()?.let { insert(key, it) }
        }
    }
}

