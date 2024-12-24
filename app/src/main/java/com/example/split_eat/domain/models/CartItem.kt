package com.example.split_eat.domain.models

import android.media.Image

data class CartItem(
    val id_product: Int,
    val name: String,
    val restaurant: String,
    val image: String?,
    val price: Double,
    var quantity: Int
)