package com.example.andoriod_midori.utils

import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.system.measureTimeMillis
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun rememberPerformanceTimer(key: String): PerformanceTimer {
    return remember(key) { PerformanceTimer(key) }
}

class PerformanceTimer(private val tag: String) {
    private var startTime: Long = 0
    
    fun start() {
        startTime = System.currentTimeMillis()
    }
    
    fun end() {
        val duration = System.currentTimeMillis() - startTime
        if (duration > 16) {
            Timber.w("Performance warning: $tag took ${duration}ms (> 16ms)")
        } else {
            Timber.d("Performance: $tag completed in ${duration}ms")
        }
    }
}

@Composable
fun <T> PerformanceTracker(
    name: String,
    content: @Composable () -> T
): T {
    val timer = rememberPerformanceTimer(name)
    
    DisposableEffect(name) {
        timer.start()
        onDispose {
            timer.end()
        }
    }
    
    return content()
}

inline fun <T> measurePerformance(
    tag: String,
    crossinline block: () -> T
): T {
    var result: T
    val time = measureTimeMillis {
        result = block()
    }
    
    if (time > 100) {
        Timber.w("Slow operation: $tag took ${time}ms")
    } else {
        Timber.d("Operation: $tag completed in ${time}ms")
    }
    
    return result
}

fun CoroutineScope.launchWithPerformanceTracking(
    tag: String,
    block: suspend CoroutineScope.() -> Unit
) {
    launch(Dispatchers.Main) {
        val time = measureTimeMillis {
            block()
        }
        Timber.d("Coroutine $tag completed in ${time}ms")
    }
} 