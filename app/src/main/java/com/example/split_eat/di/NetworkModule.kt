package com.example.split_eat.di

import com.example.split_eat.data.remote.ApiClient
import com.example.split_eat.data.remote.AuthApi
import com.example.split_eat.data.remote.CartApi
import com.example.split_eat.data.remote.RestaurantApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAuthApi(apiClient: ApiClient): AuthApi {
        return apiClient.authApi
    }

    @Provides
    @Singleton
    fun provideCartApi(apiClient: ApiClient): CartApi {
        return apiClient.cartApi
    }

    @Provides
    @Singleton
    fun provideRestaurantApi(apiClient: ApiClient): RestaurantApi {
        return apiClient.restaurantApi
    }
}
