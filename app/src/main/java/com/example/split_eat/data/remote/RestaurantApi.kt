package com.example.split_eat.data.remote

import com.example.split_eat.data.model.restaurant.CategoryResponse
import com.example.split_eat.data.model.restaurant.MenuCategories
import com.example.split_eat.data.model.restaurant.MenuResponse
import com.example.split_eat.data.model.restaurant.RestaurantResponse
import com.example.split_eat.domain.models.ProductApiResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantApi {
    @GET("restaurants/list-restaurants")
    suspend fun getAllRestaurants(
        @Query("page") page: Int? = null,
        @Query("category") category: String? = null,
        @Query("search") search: String? = null
    ): Response<RestaurantResponse>

    @GET("restaurants/list-categories")
    suspend fun getAllCategories(): Response<CategoryResponse>

    @GET("restaurants/list-menu-categories")
    suspend fun getMenuCategories(
        @Query("restaurant") restaurant: String
    ): Response<MenuCategories>

    @GET("restaurants/list-restaurant-menu")
    suspend fun getMenuRestaurant(
        @Query("restaurant") restaurant: String,
        @Query("category") category: String? = null,
        @Query("search") search: String? = null
    ): Response<MenuResponse>
}