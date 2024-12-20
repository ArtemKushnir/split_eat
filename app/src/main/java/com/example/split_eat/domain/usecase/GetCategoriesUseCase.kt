package com.example.split_eat.domain.usecase

import com.example.split_eat.domain.models.CategoryApiResult
import com.example.split_eat.domain.repository.RestaurantRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val restaurantRepository: RestaurantRepository) {
    suspend operator fun invoke(): CategoryApiResult{
        return restaurantRepository.getAllCategories()
    }
}