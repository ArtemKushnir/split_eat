package com.example.split_eat.domain.repository



interface AuthRepository {
    suspend fun login(email: String, password: String): Boolean
    suspend fun register(email: String, username: String, password: String): Boolean
    suspend fun confirmEmail(email: String, code: String): Boolean
    suspend fun updateAccessToken(refreshToken: String): Boolean
}