package com.example.split_eat.domain.models

data class Restaurant (
    val name: String,
    val logo: String,
    val free_shipping_price: Int,
    val categories: List<String>
)