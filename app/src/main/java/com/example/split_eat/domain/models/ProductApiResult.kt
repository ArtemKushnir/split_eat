package com.example.split_eat.domain.models

sealed class ProductApiResult {
    data class Success(val products: List<Product>) : ProductApiResult()
    data class Error(val code: Int?, val message: String) : ProductApiResult()
}