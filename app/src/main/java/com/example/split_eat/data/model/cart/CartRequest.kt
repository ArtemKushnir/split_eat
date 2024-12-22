package com.example.split_eat.data.model.cart

import com.example.split_eat.domain.models.CartItem

data class CartRequest (
    val restaurant: String,
    val products: List<CartItem>,
    val total_price: Double
)