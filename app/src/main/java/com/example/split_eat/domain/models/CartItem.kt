package com.example.split_eat.domain.models

import android.media.Image

data class CartItem(
    val id: String, // Уникальный идентификатор товара
    val image: String, // Картинка
    val name: String, // Название товара
    val price: Double, // Цена товара
    var quantity: Int // Количество товара
)