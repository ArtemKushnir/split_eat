package com.example.split_eat.domain.models

sealed class OrderApiResult {
    data class Success(val orders: List<Order>) : OrderApiResult()
    data class Error(val code: Int?, val message: String) : OrderApiResult()
}
