package com.example.split_eat.data.repository

import com.example.split_eat.data.remote.RestaurantApi
import com.example.split_eat.domain.models.CategoryApiResult
import com.example.split_eat.domain.models.RestaurantApiResult
import com.example.split_eat.domain.repository.RestaurantRepository
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor(
    private val restaurantApi: RestaurantApi
): RestaurantRepository {
    override suspend fun getAllRestaurants(
        page: Int?,
        categoryName: String?,
        searchText: String?
    ): RestaurantApiResult {
        val response = restaurantApi.getAllRestaurants(page, categoryName, searchText)
        return if (response.isSuccessful){
            response.body()?.let {
                RestaurantApiResult.Success(response.body()!!.results, response.body()!!.next)
            } ?: RestaurantApiResult.Error(response.code(), response.errorBody()?.string() ?: "Неизвестная ошибка")
        } else {
            RestaurantApiResult.Error(response.code(),response.errorBody()?.string() ?: "Неизвестная ошибка")
        }
    }

    override suspend fun getAllCategories(): CategoryApiResult {
        val response = restaurantApi.getAllCategories()
        return if (response.isSuccessful){
            response.body()?.let {
                CategoryApiResult.Success(response.body()!!.categories)
            } ?: CategoryApiResult.Error(response.code(),response.errorBody()?.string() ?: "Неизвестная ошибка")
        } else {
            CategoryApiResult.Error(response.code(),response.errorBody()?.string() ?: "Неизвестная ошибка")
        }
    }

    override suspend fun getMenuCategories(restaurant: String): CategoryApiResult {
        val response = restaurantApi.getMenuCategories(restaurant)
        return if (response.isSuccessful) {
            response.body()?.let {
                CategoryApiResult.Success(response.body()!!.categories)
            } ?: CategoryApiResult.Error(response.code(), response.errorBody()?.string() ?: "Неизвестная ошибка")
        } else  {
            CategoryApiResult.Error(response.code(), response.errorBody()?.string() ?: "Неизвестная ошибка")
        }
    }

}