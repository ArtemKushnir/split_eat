package com.example.split_eat.domain.models

sealed class CategoryApiResult {
    data class Success(val categories: List<String>) : CategoryApiResult()
    data class Error(val code: Int?, val message: String) : CategoryApiResult()
}