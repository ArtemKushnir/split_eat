package com.example.split_eat.data.model.restaurant

import com.example.split_eat.domain.models.Product

data class MenuResponse (
    val products: List<Product>
)