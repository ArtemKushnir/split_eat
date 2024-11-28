package com.example.split_eat.di

import com.example.split_eat.data.remote.ApiClient
import com.example.split_eat.data.remote.AuthApi
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
}
