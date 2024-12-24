package com.example.split_eat.data.repository

import com.example.split_eat.data.remote.RestaurantApi
import com.example.split_eat.domain.models.CategoryApiResult
import com.example.split_eat.domain.models.ProductApiResult
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
    ): RestaurantApiResult? {
        val response = restaurantApi.getAllRestaurants(page, categoryName, searchText)
        if (response.code() == 401) return null
        return if (response.isSuccessful) {
            response.body()?.let {
                RestaurantApiResult.Success(response.body()!!.results, response.body()!!.next)
            } ?: RestaurantApiResult.Error(
                response.code(),
                response.errorBody()?.string() ?: "Неизвестная ошибка"
            )
        } else {
            RestaurantApiResult.Error(
                response.code(),
                response.errorBody()?.string() ?: "Неизвестная ошибка"
            )
        }
    }

    override suspend fun getAllCategories(): CategoryApiResult? {
        val response = restaurantApi.getAllCategories()
        if (response.code() == 401) return null
        return if (response.isSuccessful) {
            response.body()?.let {
                CategoryApiResult.Success(response.body()!!.categories)
            } ?: CategoryApiResult.Error(
                response.code(),
                response.errorBody()?.string() ?: "Неизвестная ошибка"
            )
        } else {
            CategoryApiResult.Error(
                response.code(),
                response.errorBody()?.string() ?: "Неизвестная ошибка"
            )
        }
    }

    override suspend fun getMenuCategories(restaurant: String): CategoryApiResult? {
        val response = restaurantApi.getMenuCategories(restaurant)
        if (response.code() == 401) return null
        return if (response.isSuccessful) {
            response.body()?.let {
                CategoryApiResult.Success(response.body()!!.categories)
            } ?: CategoryApiResult.Error(
                response.code(),
                response.errorBody()?.string() ?: "Неизвестная ошибка"
            )
        } else {
            CategoryApiResult.Error(
                response.code(),
                response.errorBody()?.string() ?: "Неизвестная ошибка"
            )
        }
    }


    override suspend fun getMenuRestaurant(
        restaurant: String,
        category: String?,
        search: String?
    ): ProductApiResult? {
        val response = restaurantApi.getMenuRestaurant(restaurant, category, search)
        if (response.code() == 401) return null
        return if (response.isSuccessful) {
            response.body()?.let {
                ProductApiResult.Success(response.body()!!.products)
            } ?: ProductApiResult.Error(response.code(), response.errorBody()?.string() ?: "Неизвестная ошибка")
        } else {
            ProductApiResult.Error(response.code(), response.errorBody()?.string() ?: "Неизвестная ошибка")
        }
    }
}