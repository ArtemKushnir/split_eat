package com.example.split_eat.data.model.auth


data class TokenResponse (
    val message: String,
    val access: String,
    val refresh: String
)