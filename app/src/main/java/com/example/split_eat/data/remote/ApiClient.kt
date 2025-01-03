package com.example.split_eat.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class ApiClient @Inject constructor(
    private val authorizationInterceptor: AuthorizationInterceptor,
    private val tokenAuthenticator: TokenAuthenticator
) {

    private val BASE_URL = "http://10.0.2.2:8000/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(authorizationInterceptor)
        .authenticator(tokenAuthenticator)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val restaurantApi: RestaurantApi = retrofit.create(RestaurantApi::class.java)
    val orderApi: OrderApi = retrofit.create(OrderApi::class.java)
    val cartApi: CartApi = retrofit.create(CartApi::class.java)
}
