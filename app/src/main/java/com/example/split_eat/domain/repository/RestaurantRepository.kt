package com.example.split_eat.domain.repository

import com.example.split_eat.domain.models.CategoryApiResult
import com.example.split_eat.domain.models.RestaurantApiResult

interface RestaurantRepository {
    suspend fun getAllRestaurants(page: Int?, categoryName: String?, searchText: String?): RestaurantApiResult

    suspend fun getAllCategories(): CategoryApiResult
}