package com.example.split_eat.domain.models

import android.media.Image

data class CartItem(
    // val image: Image,
    val name: String,
    val price: Double,
    var quantity: Int
)