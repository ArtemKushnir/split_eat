package com.example.split_eat.data.remote

import com.example.split_eat.data.model.ConfirmEmailRequest
import com.example.split_eat.data.model.LoginRequest
import com.example.split_eat.data.model.UpdateAccessTokenRequest
import com.example.split_eat.data.model.RegisterRequest
import com.example.split_eat.data.model.RegisterResponse
import com.example.split_eat.data.model.TokenResponse
import com.example.split_eat.data.model.UpdateAccessTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("authentication/token/")
    suspend fun login(@Body loginRequest: LoginRequest): TokenResponse

    @POST("authentication/register/")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("authentication/confirm-email/")
    suspend fun confirmEmail(@Body confirmEmailRequest: ConfirmEmailRequest): TokenResponse

    @POST("authentication/token/refresh/")
    suspend fun updateAccessToken(@Body refreshTokensRequest: UpdateAccessTokenRequest): UpdateAccessTokenResponse
}