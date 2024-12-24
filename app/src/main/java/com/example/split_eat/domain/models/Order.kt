package com.example.split_eat.domain.models

data class Order(
    val id: Int,
    val restaurant: String,
    val status: String,
    val total_price: Int,
    val prod_images: List<Any>,
    val prod_names: MutableList<String>
)