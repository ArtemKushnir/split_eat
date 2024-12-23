package com.example.split_eat.data.model.auth

data class ConfirmEmailRequest (
    val email: String,
    val code: String
)