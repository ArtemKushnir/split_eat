package com.example.split_eat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.split_eat.domain.models.CartAPIResult
import com.example.split_eat.domain.models.CartItem
import com.example.split_eat.domain.usecase.CartUseCase
import com.example.split_eat.data.local.CartStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class CartViewModel @Inject constructor(
    private val cartUseCase: CartUseCase,
    private val cartStorage: CartStorage
) : ViewModel() {

    private val _messageEvent = MutableSharedFlow<String>()
    val messageEvent = _messageEvent.asSharedFlow()

    // Используем mutableStateListOf для отслеживания изменений в списке
    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> get() = _cartItems

    init {
        // Инициализация списка корзины при старте
        _cartItems.addAll(cartStorage.getItems())
    }

    // Обновляем список товаров, когда его количество изменяется
    private fun updateCartItems() {
        _cartItems.clear()
        _cartItems.addAll(cartStorage.getItems())
    }

    // Увеличиваем количество товара в корзине
    fun increaseQuantity(item: CartItem) {
        cartStorage.addItem(item)
        updateCartItems() // Обновляем список товаров
    }

    // Уменьшаем количество товара в корзине
    fun decreaseQuantity(item: CartItem) {
        cartStorage.decreaseItemQuantity(item)
        updateCartItems() // Обновляем список товаров
    }

    // Получаем общую сумму всех товаров в корзине
    fun getTotalPrice(): Double {
        return cartStorage.getItems().sumOf { it.price * it.quantity }
    }

    // Отправляем корзину на сервер
    fun send_cart() {
        viewModelScope.launch {
            try {
                when (val response = cartUseCase(cartStorage.getItems()[0].restaurant, cartStorage.getItems(), getTotalPrice())) { // Сделать для разных ресторанов
                    is CartAPIResult.Success -> _messageEvent.emit(response.message)
                    is CartAPIResult.Error -> _messageEvent.emit(response.message)
                }
            } catch (e: Exception) {
                _messageEvent.emit(e.message ?: "Ошибка отправки корзины")
            }
        }
    }
}
