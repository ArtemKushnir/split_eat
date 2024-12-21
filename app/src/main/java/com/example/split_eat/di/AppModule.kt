package com.example.split_eat.di

import com.example.split_eat.data.repository.AuthRepositoryImpl
import com.example.split_eat.data.repository.CartRepositoryImpl
import com.example.split_eat.data.repository.RestaurantRepositoryImpl
import com.example.split_eat.domain.repository.AuthRepository
import com.example.split_eat.domain.repository.CartRepository
import com.example.split_eat.domain.repository.RestaurantRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(
        userRepositoryImpl: CartRepositoryImpl
    ): CartRepository

    @Binds
    @Singleton
    abstract fun bindRestaurantRepository(
        restaurantRepositoryImpl: RestaurantRepositoryImpl
    ): RestaurantRepository
}
