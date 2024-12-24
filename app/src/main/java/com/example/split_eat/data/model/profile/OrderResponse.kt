package com.example.split_eat.data.model.profile

import com.example.split_eat.domain.models.Order
import com.example.split_eat.domain.models.Restaurant

data class OrderResponse (
    val results: List<Order>
)