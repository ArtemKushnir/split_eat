package com.example.split_eat.domain.models

sealed class RestaurantApiResult {
    data class Success(val restaurants: List<Restaurant>, val next: String?) : RestaurantApiResult()
    data class Error(val code: Int?, val message: String) : RestaurantApiResult()
}