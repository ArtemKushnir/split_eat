package com.example.split_eat.domain.repository

import com.example.split_eat.domain.models.ApiResult
import com.example.split_eat.domain.models.UserStatus


interface AuthRepository {
    suspend fun login(email: String, password: String): ApiResult
    suspend fun register(email: String, username: String, password: String): ApiResult
    suspend fun confirmEmail(email: String, code: String): ApiResult
    suspend fun updateAccessToken()
    suspend fun checkUserStatus(email: String): UserStatus
}