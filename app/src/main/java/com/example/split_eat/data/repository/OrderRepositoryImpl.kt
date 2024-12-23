package com.example.split_eat.data.repository

import com.example.split_eat.domain.models.Order
import com.example.split_eat.domain.repository.OrderRepository
import javax.inject.Inject // Add Inject

class OrderRepositoryImpl @Inject constructor() : OrderRepository {
    override suspend fun getActiveOrders(): List<Order> {
        return listOf(
            Order(1, "Покупка продуктов", "В процессе"),
            Order(2, "Заказ техники", "Ожидает отправки"),
        )
    }

    override suspend fun getCompletedOrders(): List<Order> {
        return listOf(
            Order(3, "Доставка цветов", "Завершен"),
            Order(4, "Ремонт телефона", "Завершен"),
        )
    }
}