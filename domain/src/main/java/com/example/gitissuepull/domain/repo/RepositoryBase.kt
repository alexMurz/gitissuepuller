package com.example.gitissuepull.domain.repo

interface IRepositoryBase<T> {
    fun addListener(l: T): Boolean
    fun removeListener(l: T): Boolean
}

open class RepositoryBase<T>: IRepositoryBase<T> {
    protected val listeners = HashSet<T>()

    override fun addListener(l: T): Boolean {
        val b = listeners.add(l)
        onListenerAdded(l)
        return b
    }
    override fun removeListener(l: T): Boolean {
        val b = listeners.remove(l)
        onListenerRemoved(l)
        return b
    }

    open fun onListenerAdded(l: T) {}
    open fun onListenerRemoved(l: T) {}

    protected inline fun callback(func: T.() -> Unit) {
        for (listener in listeners) listener.func()
    }

}