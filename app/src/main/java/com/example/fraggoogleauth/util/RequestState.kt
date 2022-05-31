package com.example.fraggoogleauth.util

sealed class RequestState<out T> {
    object Idle : RequestState<Nothing>()
    object Loading : RequestState<Nothing>()
    data class Success<T>(val data: T, val message: String? = null) : RequestState<T>()
    data class Error(
        val error: Exception
    ) : RequestState<Nothing>()
}