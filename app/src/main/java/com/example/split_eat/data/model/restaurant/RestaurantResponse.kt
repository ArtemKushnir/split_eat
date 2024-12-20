package com.example.split_eat.data.model.restaurant

import com.example.split_eat.domain.models.Restaurant

data class RestaurantResponse (
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Restaurant>
)