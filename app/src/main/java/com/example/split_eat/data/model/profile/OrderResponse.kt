package com.example.split_eat.data.model.profile

import com.example.split_eat.domain.models.Order

data class OrderResponse (
    val carts: List<Order>
)