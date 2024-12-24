package com.example.split_eat.domain.repository

import com.example.split_eat.domain.models.AdminOrder

interface AdminOrderRepository {
    suspend fun getActiveAdminOrders(): List<AdminOrder>
    suspend fun getCompletedAdminOrders(): List<AdminOrder>
}