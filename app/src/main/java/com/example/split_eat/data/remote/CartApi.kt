package com.example.split_eat.data.remote

import com.example.split_eat.data.model.CartRequest
import com.example.split_eat.data.model.CartResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CartApi {
    @POST("cart/")
    suspend fun cart(@Body cartRequest: CartRequest): Response<CartResponse>
}