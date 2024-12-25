package com.example.split_eat.domain.models

data class Product (
    val id: Int,
    val name: String,
    val image: String?,
    val price: Double,
    val weight: Double?,
    val weight_unit: String?,
    val calories: Double?
)