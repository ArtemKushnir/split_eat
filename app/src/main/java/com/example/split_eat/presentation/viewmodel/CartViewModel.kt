package com.example.split_eat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.split_eat.data.local.CartStorage
import com.example.split_eat.domain.models.CartAPIResult
import com.example.split_eat.domain.models.CartItem
import com.example.split_eat.domain.usecase.CartUseCase
import com.example.split_eat.domain.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartUseCase: CartUseCase,
    private val cartStorage: CartStorage
) : ViewModel() {

    private val _messageEvent = MutableSharedFlow<String>()
    val messageEvent = _messageEvent.asSharedFlow()

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> get() = _cartItems

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> get() = _totalPrice


    init {
        cartStorage.cartItemsFlow.onEach { items ->
            _cartItems.value = items
            calculateTotalPrice()
        }.launchIn(viewModelScope)
    }

    private fun calculateTotalPrice() {
        _totalPrice.value = _cartItems.value.sumOf { it.price * it.quantity }
    }

    fun increaseQuantity(item: CartItem) {
        cartStorage.addItem(item)
    }

    fun decreaseQuantity(item: CartItem) {
        cartStorage.decreaseItemQuantity(item)
    }

    fun send_cart() {
        viewModelScope.launch {
            try {
                when (val response = cartUseCase(cartStorage.getItems()[0].restaurant, cartStorage.getItems(), totalPrice.value)) {
                    is CartAPIResult.Success -> _messageEvent.emit(response.message)
                    is CartAPIResult.Error -> _messageEvent.emit(response.message)
                }
            } catch (e: Exception) {
                _messageEvent.emit(e.message ?: "Ошибка отправки корзины")
            }
        }
        cartStorage.clearCart()
    }
}
