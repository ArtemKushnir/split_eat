package com.example.split_eat.utils

sealed class Resource<out T> {
    data object Idle : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T? = null) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
}
