package com.example.split_eat.data.repository

import com.example.split_eat.data.model.cart.CartRequest
import com.example.split_eat.data.remote.CartApi
import com.example.split_eat.domain.models.CartAPIResult
import com.example.split_eat.domain.models.CartItem
import com.example.split_eat.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartApi: CartApi
): CartRepository{
    override suspend fun cart(
        products: MutableList<CartItem>
    ): CartAPIResult {
        val response = cartApi.cart(CartRequest(products))
        return if (response.isSuccessful) {
            response.body()?.let {
                CartAPIResult.Success(response.body()!!.message)
            } ?: CartAPIResult.Error(response.code(), response.message())
        } else {
            CartAPIResult.Error(
                response.code(),
                response.errorBody()?.string()?.filter { it in 'А'..'я' || it.isWhitespace() }?:"Неизвестная ошибка")
        }
    }
}