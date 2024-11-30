package com.example.split_eat.domain.models

sealed class ApiResult {
    data object Success : ApiResult()
    data class Error(val code: Int?, val message: String) : ApiResult()
}
