package com.example.split_eat.domain.models

import android.media.Image

data class CartItem(
    val name: String, // Название товара
    val restaurant: String, // Ресторан
    val image: String?, // Картинка
    val price: Double, // Цена товара
    var quantity: Int // Количество товара
)