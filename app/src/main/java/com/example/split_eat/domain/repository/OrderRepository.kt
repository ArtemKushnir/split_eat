package com.example.split_eat.domain.repository

import com.example.split_eat.domain.models.OrderApiResult

interface OrderRepository {
    suspend fun getActiveOrders(user: String, status: String): OrderApiResult?
    suspend fun getCompletedOrders(user: String, status: String): OrderApiResult?
}