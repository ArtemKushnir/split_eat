package com.example.split_eat.domain.models

data class _Product(
    val name: String,
    val image: String
)

data class _CartItem(
    val id_product: _Product,
    val quantity: String
)


data class Order (
    val id: Int,
    val user: String,
    val restaurant: String,
    val products: List<_CartItem>,
    val total_price: Double,
    val status: Boolean?,
    val created_at: String
)

