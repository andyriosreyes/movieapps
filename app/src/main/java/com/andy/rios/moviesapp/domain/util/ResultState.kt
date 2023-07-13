package com.andy.rios.moviesapp.domain.util

sealed class ResultState<T> {
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error<T>(val error: Throwable) : ResultState<T>()
}

inline fun <T, R> ResultState<T>.getResult(
    success: (ResultState.Success<T>) -> R,
    error: (ResultState.Error<T>) -> R
): R = if (this is ResultState.Success) success(this) else error(this as ResultState.Error)

inline fun <T> ResultState<T>.onSuccess(
    block: (T) -> Unit
): ResultState<T> = if (this is ResultState.Success) also { block(data) } else this

inline fun <T> ResultState<T>.onError(
    block: (Throwable) -> Unit
): ResultState<T> = if (this is ResultState.Error) also { block(error) } else this
