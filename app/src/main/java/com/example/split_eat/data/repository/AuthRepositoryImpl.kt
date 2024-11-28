package com.example.split_eat.data.repository

import com.example.split_eat.data.local.TokenStorage
import com.example.split_eat.data.remote.AuthApi
import com.example.split_eat.data.model.ConfirmEmailRequest
import com.example.split_eat.data.model.LoginRequest
import com.example.split_eat.data.model.RegisterRequest
import com.example.split_eat.data.model.UpdateAccessTokenRequest
import com.example.split_eat.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenStorage: TokenStorage
): AuthRepository{
    override suspend fun login(email: String, password: String): Boolean {
        val response = authApi.login(LoginRequest(email, password))
        if (response.refreshToken.isNotEmpty() and response.accessToken.isNotEmpty()){
            tokenStorage.saveTokens(response.accessToken, response.refreshToken)
            return true
        }
        return false
    }

    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): Boolean {
        val response = authApi.register(RegisterRequest(email, username, password))
        return response.message == "Registration successful"
    }

    override suspend fun confirmEmail(email: String, code: String): Boolean {
        val response = authApi.confirmEmail(ConfirmEmailRequest(email, code))
        if (response.refreshToken.isNotEmpty() and response.accessToken.isNotEmpty()){
            tokenStorage.saveTokens(response.accessToken, response.refreshToken)
            return true
        }
        return false
    }

    override suspend fun updateAccessToken(refreshToken: String): Boolean {
        val response = authApi.updateAccessToken(UpdateAccessTokenRequest(refreshToken))
        if (response.accessToken.isNotEmpty()){
            tokenStorage.updateAccessToken(response.accessToken)
            return true
        }
        return false
    }
}