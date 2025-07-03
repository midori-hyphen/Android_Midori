package com.example.andoriod_midori.data.ui

import androidx.compose.runtime.Stable

@Stable
sealed interface UiState<out T> {
    
    data object Idle : UiState<Nothing>
    
    data object Loading : UiState<Nothing>
    
    data class Success<T>(val data: T) : UiState<T>
    
    data class Error(
        val throwable: Throwable,
        val message: String = throwable.message ?: "Unknown error occurred"
    ) : UiState<Nothing>
}

sealed class MidoriError : Exception() {
    
    data class NetworkError(
        override val message: String = "Network connection failed"
    ) : MidoriError()
    
    data class DataError(
        override val message: String = "Data processing failed"
    ) : MidoriError()
    
    data class WidgetError(
        val widgetId: String,
        override val message: String = "Widget operation failed"
    ) : MidoriError()
    
    data class AppError(
        override val message: String = "Application error occurred"
    ) : MidoriError()
}

fun <T> UiState<T>.isLoading(): Boolean = this is UiState.Loading
fun <T> UiState<T>.isSuccess(): Boolean = this is UiState.Success
fun <T> UiState<T>.isError(): Boolean = this is UiState.Error
fun <T> UiState<T>.isIdle(): Boolean = this is UiState.Idle

fun <T> UiState<T>.getDataOrNull(): T? = when (this) {
    is UiState.Success -> data
    else -> null
}

fun <T> UiState<T>.getErrorOrNull(): UiState.Error? = when (this) {
    is UiState.Error -> this
    else -> null
}

inline fun <T, R> UiState<T>.mapData(transform: (T) -> R): UiState<R> = when (this) {
    is UiState.Success -> UiState.Success(transform(data))
    is UiState.Error -> this
    is UiState.Loading -> this
    is UiState.Idle -> this
} 