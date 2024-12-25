package com.example.split_eat.di

import androidx.lifecycle.ViewModel
import com.example.split_eat.data.repository.AuthRepositoryImpl
import com.example.split_eat.data.repository.OrderRepositoryImpl
import com.example.split_eat.data.repository.RestaurantRepositoryImpl
import com.example.split_eat.domain.repository.AuthRepository
import com.example.split_eat.domain.repository.OrderRepository
import com.example.split_eat.data.repository.CartRepositoryImpl
import com.example.split_eat.data.repository.RestaurantRepositoryImpl
import com.example.split_eat.domain.repository.AuthRepository
import com.example.split_eat.domain.repository.CartRepository
import com.example.split_eat.domain.repository.RestaurantRepository
import com.example.split_eat.presentation.viewmodel.OrderViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
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
    @Binds
    @Singleton
    abstract fun bindOrderRepository(
        orderRepositoryImpl: OrderRepositoryImpl
    ): OrderRepository
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @StringKey("com.example.myapp.presentation.viewmodel.OrderViewModel")
    abstract fun bindOrderViewModel(viewModel: OrderViewModel): ViewModel
}
