package com.example.gitissuepull.repo.base

interface IRepositoryBase<T> {
    fun addListener(l: T): Boolean
    fun removeListener(l: T): Boolean
}

open class RepositoryBase<T>: IRepositoryBase<T> {
    protected val listeners = HashSet<T>()
    override fun addListener(l: T) = listeners.add(l)
    override fun removeListener(l: T) = listeners.remove(l)
    protected inline fun callback(func: T.() -> Unit) {
        for (listener in listeners) listener.func()
    }

}