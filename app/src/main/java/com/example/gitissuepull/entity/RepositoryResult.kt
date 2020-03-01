package com.example.gitissuepull.entity

class RepositoryResult<T> private constructor(
    private val value: T? = null,
    private val error: Throwable? = null
)
{
    companion object {
        fun <T> fromValue(value: T) = RepositoryResult(value, null)
        fun <T> fromError(error: Throwable) = RepositoryResult<T>(null, error)
    }
    enum class Type { Value, Error }

    fun type() = if (value == null) Type.Error else Type.Value
    fun getValue(): T = value!!
    fun getError() = error!!
}