package com.example.split_eat.data.remote

import com.example.split_eat.data.model.auth.ConfirmEmailRequest
import com.example.split_eat.data.model.auth.LoginRequest
import com.example.split_eat.data.model.auth.UpdateAccessTokenRequest
import com.example.split_eat.data.model.auth.RegisterRequest
import com.example.split_eat.data.model.auth.RegisterResponse
import com.example.split_eat.data.model.auth.StatusRequest
import com.example.split_eat.data.model.auth.StatusResponse
import com.example.split_eat.data.model.auth.TokenResponse
import com.example.split_eat.data.model.auth.UpdateAccessTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("authentication/token/")
    suspend fun login(@Body loginRequest: LoginRequest): Response<TokenResponse>

    @POST("authentication/register/")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST("authentication/confirm-email/")
    suspend fun confirmEmail(@Body confirmEmailRequest: ConfirmEmailRequest): Response<TokenResponse>

    @POST("authentication/token/refresh/")
    suspend fun updateAccessToken(@Body refreshTokensRequest: UpdateAccessTokenRequest): Response<UpdateAccessTokenResponse>

    @POST("authentication/get-user-status/")
    suspend fun checkUserStatus(@Body email:StatusRequest): Response<StatusResponse>
}