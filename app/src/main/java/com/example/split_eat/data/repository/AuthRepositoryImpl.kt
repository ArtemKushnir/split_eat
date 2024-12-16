package com.example.split_eat.data.repository

import com.example.split_eat.data.local.TokenStorage
import com.example.split_eat.data.remote.AuthApi
import com.example.split_eat.data.model.ConfirmEmailRequest
import com.example.split_eat.data.model.LoginRequest
import com.example.split_eat.data.model.RegisterRequest
import com.example.split_eat.data.model.UpdateAccessTokenRequest
import com.example.split_eat.domain.models.ApiResult
import com.example.split_eat.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenStorage: TokenStorage
): AuthRepository{
    override suspend fun login(email: String, password: String): ApiResult {
        val response = authApi.login(LoginRequest(email, password))
        return if (response.isSuccessful){
            response.body()?.let {
                tokenStorage.saveTokens(it.access, it.refresh)
                ApiResult.Success
            } ?: ApiResult.Error(response.code(), response.message())
        } else {ApiResult.Error(response.code(), "Такой пользователь не зарегистрирован")}
    }

    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): ApiResult {
        val response = authApi.register(RegisterRequest(email, username, password))
        return if (response.isSuccessful) {
            ApiResult.Success
        } else {
            ApiResult.Error(
                response.code(),
                response.errorBody()?.string()?.filter { it in 'А'..'я' || it.isWhitespace() }?:"Неизвестная ошибка")
        }
    }

    override suspend fun confirmEmail(email: String, code: String): ApiResult {
        val response = authApi.confirmEmail(ConfirmEmailRequest(email, code))
        return if (response.isSuccessful){
            response.body()?.let {
                tokenStorage.saveTokens(it.access, it.refresh)
                ApiResult.Success
            } ?: ApiResult.Error(response.code(), "Неизвестная ошибка")
        } else {
            ApiResult.Error(
                response.code(),
                response.errorBody()?.string()?.filter { it in 'А'..'я' || it.isWhitespace()}?:"Неизвестная ошибка")}
    }

    override suspend fun updateAccessToken(refreshToken: String): ApiResult {
        val response = authApi.updateAccessToken(UpdateAccessTokenRequest(refreshToken))
        return if (response.isSuccessful){
            response.body()?.let {
                tokenStorage.updateAccessToken(it.accessToken)
                ApiResult.Success
            } ?: ApiResult.Error(response.code(), "Неизвестная ошибка")
        } else {
            ApiResult.Error(
                response.code(),
                response.errorBody()?.string()?.filter { it in 'А'..'я' || it.isWhitespace()}?:"Неизвестная ошибка")}
    }
}