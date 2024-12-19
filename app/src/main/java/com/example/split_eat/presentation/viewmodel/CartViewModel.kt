package com.example.split_eat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.split_eat.domain.models.CartAPIResult
import com.example.split_eat.domain.models.CartItem
import com.example.split_eat.domain.usecase.CartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class CartViewModel @Inject constructor(
    private val cartUseCase: CartUseCase
) : ViewModel() {
    private val _messageEvent = MutableSharedFlow<String>()
    val messageEvent = _messageEvent.asSharedFlow()

    open val cartItems: MutableList<CartItem> = mutableStateListOf(
        CartItem("Товар 1", 100.0, 1),
        CartItem("Товар 2", 200.0, 2),
        CartItem("Товар 3", 300.0, 1)
    )

    fun increaseQuantity(item: CartItem) {
        val index = cartItems.indexOf(item)
        if (index != -1) cartItems[index] = item.copy(quantity = item.quantity + 1)
    }

    fun decreaseQuantity(item: CartItem) {
        val index = cartItems.indexOf(item)
        if (index != -1 && item.quantity > 1) {
            cartItems[index] = item.copy(quantity = item.quantity - 1)
        }
    }

    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.price * it.quantity }
    }

    fun send_cart() {
        viewModelScope.launch {
            try {
                when (val response = cartUseCase(cartItems)) {
                    is CartAPIResult.Success -> _messageEvent.emit(response.message)
                    is CartAPIResult.Error -> _messageEvent.emit(response.message)
                }
            } catch (e: Exception) {
                _messageEvent.emit(e.message ?: "Ошибка отправки корзины")
            }
        }
    }
}
