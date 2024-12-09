package com.example.split_eat.di

import com.example.split_eat.data.repository.AuthRepositoryImpl
import com.example.split_eat.domain.repository.AuthRepository
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
    abstract fun bindUserRepository(
        userRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}
