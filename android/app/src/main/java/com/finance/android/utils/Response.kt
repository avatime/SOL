package com.finance.android.utils

sealed class Response<out T> {
    object Loading : Response<Nothing>()

    data class Success<out T>(
        val data: T
    ) : Response<T>() {
        override fun toString(): String {
            return "Success(data: $data)"
        }
    }

    data class Failure(
        val e: Exception?
    ) : Response<Nothing>()
}
