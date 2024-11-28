package com.example.split_eat.di

import android.app.Application
import android.content.Context
import com.example.split_eat.data.local.TokenStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object LocalStorageModule {

    @Provides
    fun provideTokenStorage(context: Context): TokenStorage {
        return TokenStorage(context)
    }
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
}