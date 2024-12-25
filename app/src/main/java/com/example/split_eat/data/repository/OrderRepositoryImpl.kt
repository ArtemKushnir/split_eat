package com.example.split_eat.data.repository

import com.example.split_eat.data.remote.OrderApi
import com.example.split_eat.domain.models.OrderApiResult
import com.example.split_eat.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val orderApi: OrderApi) : OrderRepository {
    override suspend fun getActiveOrders(user: String, status: String): OrderApiResult? {
        val response = orderApi.getActiveOrders(user, status)
        if (response.code() == 401) return null
        return if (response.isSuccessful) {
            response.body()?.let {
                OrderApiResult.Success(response.body()!!.carts)
            } ?: OrderApiResult.Error(
                response.code(),
                response.errorBody()?.string() ?: "Неизвестная ошибка"
            )
        } else {
            OrderApiResult.Error(
                response.code(),
                response.errorBody()?.string() ?: "Неизвестная ошибка"
            )
        }
    }

    override suspend fun getCompletedOrders(user: String, status: String): OrderApiResult? {
        val response = orderApi.getCompletedOrders(user, status)
        if (response.code() == 401) return null
        return if (response.isSuccessful) {
            response.body()?.let {
                OrderApiResult.Success(response.body()!!.carts)
            } ?: OrderApiResult.Error(
                response.code(),
                response.errorBody()?.string() ?: "Неизвестная ошибка"
            )
        } else {
            OrderApiResult.Error(
                response.code(),
                response.errorBody()?.string() ?: "Неизвестная ошибка"
            )
        }

    }
}