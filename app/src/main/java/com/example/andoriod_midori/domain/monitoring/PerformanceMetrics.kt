package com.example.andoriod_midori.domain.monitoring

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.system.measureTimeMillis

object PerformanceMetrics {
    
    data class WidgetOperationMetric(
        val operation: String,
        val widgetType: String,
        val duration: Long,
        val success: Boolean,
        val timestamp: Long = System.currentTimeMillis()
    )
    
    fun trackWidgetOperation(
        operation: String,
        widgetType: String,
        block: () -> Unit
    ) {
        var success = true
        val duration = measureTimeMillis {
            try {
                block()
            } catch (e: Exception) {
                success = false
                throw e
            }
        }
        
        logMetric(WidgetOperationMetric(operation, widgetType, duration, success))
        
        if (duration > 100) {
            Timber.w("Slow widget operation: $operation on $widgetType took ${duration}ms")
        }
    }
    
    private fun logMetric(metric: WidgetOperationMetric) {
        CoroutineScope(Dispatchers.IO).launch {
            Timber.d("Widget metric: ${metric.operation} ${metric.widgetType} success=${metric.success}")
        }
    }
} 