package com.example.split_eat.domain.usecase

import com.example.split_eat.domain.models.CartAPIResult
import com.example.split_eat.domain.models.CartItem
import com.example.split_eat.domain.repository.CartRepository
import javax.inject.Inject

class CartUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend operator fun invoke(restaurant: String, products: List<CartItem>, total_price: Double): CartAPIResult {
        return cartRepository.cart(restaurant, products, total_price)
    }
}