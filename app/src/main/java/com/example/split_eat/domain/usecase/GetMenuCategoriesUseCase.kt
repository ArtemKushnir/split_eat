package com.example.split_eat.domain.usecase

import com.example.split_eat.domain.models.CategoryApiResult
import com.example.split_eat.domain.repository.RestaurantRepository
import javax.inject.Inject

class GetMenuCategoriesUseCase @Inject constructor(private val restaurantRepository: RestaurantRepository) {
    suspend operator fun invoke(restaurant: String): CategoryApiResult? {
        return restaurantRepository.getMenuCategories(restaurant)
    }
}