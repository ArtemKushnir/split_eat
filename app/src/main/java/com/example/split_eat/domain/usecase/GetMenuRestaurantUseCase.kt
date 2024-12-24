package com.example.split_eat.domain.usecase

import com.example.split_eat.domain.models.ProductApiResult
import com.example.split_eat.domain.repository.RestaurantRepository
import javax.inject.Inject

class GetMenuRestaurantUseCase @Inject constructor(private val restaurantRepository: RestaurantRepository) {
    suspend operator fun invoke(restaurant: String, category: String?, search: String?): ProductApiResult {
        return restaurantRepository.getMenuRestaurant(restaurant, category, search)
    }
}