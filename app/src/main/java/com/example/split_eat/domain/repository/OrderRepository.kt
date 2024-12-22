package com.example.split_eat.domain.repository

import com.example.split_eat.domain.models.Order

interface OrderRepository {
    suspend fun getActiveOrders(): List<Order>
    suspend fun getCompletedOrders(): List<Order>
}