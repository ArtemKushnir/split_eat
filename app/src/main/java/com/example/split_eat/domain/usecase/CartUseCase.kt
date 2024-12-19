package com.example.split_eat.domain.usecase

import com.example.split_eat.domain.models.CartAPIResult
import com.example.split_eat.domain.models.CartItem
import com.example.split_eat.domain.repository.CartRepository
import javax.inject.Inject

class CartUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend operator fun invoke(products: MutableList<CartItem>): CartAPIResult {
        return cartRepository.cart(products)
    }
}