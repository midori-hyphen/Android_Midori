package com.example.andoriod_midori.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

data class CacheEntry<T>(
    val data: T,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun isExpired(cacheDuration: Duration): Boolean {
        return System.currentTimeMillis() - timestamp > cacheDuration.inWholeMilliseconds
    }
}

class MemoryCache<K, V>(
    private val defaultCacheDuration: Duration = 5.minutes
) {
    private val cache = mutableMapOf<K, CacheEntry<V>>()
    private val mutex = Mutex()

    suspend fun get(key: K): V? = mutex.withLock {
        val entry = cache[key]
        if (entry?.isExpired(defaultCacheDuration) == true) {
            cache.remove(key)
            null
        } else {
            entry?.data
        }
    }

    suspend fun put(key: K, value: V): Unit = mutex.withLock {
        cache[key] = CacheEntry(value)
    }

    suspend fun clear(): Unit = mutex.withLock {
        cache.clear()
    }

    suspend fun remove(key: K): Unit = mutex.withLock {
        cache.remove(key)
    }
}

fun <T> Flow<T>.cacheStrategy(
    useCache: Boolean = true,
    cacheKey: String? = null
): Flow<T> {
    return if (useCache && cacheKey != null) {
        this
    } else {
        this
    }
} 