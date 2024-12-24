package com.example.split_eat.data.remote

import com.example.split_eat.data.model.profile.OrderResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OrderApi {
    @GET("cart/orders-user-active")
    suspend fun getActiveOrders(
        @Query("user") restaurant: String,
        @Query("status") category: String,
    ): Response<OrderResponse>
    @GET("cart/orders-user-active")
    suspend fun getCompletedOrders(
        @Query("user") restaurant: String,
        @Query("status") category: String,
    ): Response<OrderResponse>
}