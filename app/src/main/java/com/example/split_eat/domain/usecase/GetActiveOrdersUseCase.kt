package com.example.split_eat.domain.usecase

import com.example.split_eat.domain.models.OrderApiResult
import com.example.split_eat.domain.repository.OrderRepository
import javax.inject.Inject

class GetActiveOrdersUseCase @Inject constructor(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(user: String, status: String): OrderApiResult? {
        return orderRepository.getActiveOrders(user, status)
    }
}

class GetCompletedOrdersUseCase @Inject constructor(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(user: String, status: String): OrderApiResult? {
        return orderRepository.getCompletedOrders(user, status)
    }
}