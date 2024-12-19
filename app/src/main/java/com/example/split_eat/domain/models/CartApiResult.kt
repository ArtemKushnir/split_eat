package com.example.split_eat.domain.models

sealed class CartAPIResult {
    data class Success(val message: String) : CartAPIResult()
    data class Error(val code: Int?, val message: String) : CartAPIResult()
}