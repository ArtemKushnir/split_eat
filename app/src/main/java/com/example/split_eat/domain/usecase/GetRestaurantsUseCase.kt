package com.example.split_eat.domain.usecase

import com.example.split_eat.domain.models.RestaurantApiResult
import com.example.split_eat.domain.repository.RestaurantRepository
import javax.inject.Inject

class GetRestaurantsUseCase @Inject constructor(private val restaurantRepository: RestaurantRepository) {
    suspend operator fun invoke(page: Int?, categoryName: String?, searchText: String?): RestaurantApiResult? {
        return restaurantRepository.getAllRestaurants(page, categoryName, searchText)
    }
}