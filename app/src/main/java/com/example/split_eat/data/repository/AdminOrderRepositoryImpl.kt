package com.example.split_eat.data.repository

import com.example.split_eat.domain.models.AdminOrder
import com.example.split_eat.domain.repository.AdminOrderRepository
import javax.inject.Inject

class AdminOrderRepositoryImpl @Inject constructor(): AdminOrderRepository {
    // Реализация методов
    override suspend fun getActiveAdminOrders(): List<AdminOrder> {
        TODO("Not yet implemented")
    }

    override suspend fun getCompletedAdminOrders(): List<AdminOrder> {
        TODO("Not yet implemented")
    }
}