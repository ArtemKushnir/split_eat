package com.example.split_eat.domain.repository

import com.example.split_eat.domain.models.CartAPIResult
import com.example.split_eat.domain.models.CartItem

interface CartRepository {
    suspend fun cart(restaurant: String, products: List<CartItem>, total_price: Double): CartAPIResult
}