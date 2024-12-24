package com.example.split_eat.domain.models


data class AdminOrder(
    val id: Int,
    val title: String,
    val logo: String,
    val images: List<String>,
    val participants: List<String> = emptyList(),
    val status: String = "В ожидании"
)
