package com.example.split_eat.data.model

import com.example.split_eat.domain.models.CartItem

data class CartRequest (
    val products: MutableList<CartItem>
)