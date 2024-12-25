package com.example.split_eat.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.split_eat.domain.models.Order
import com.example.split_eat.domain.models.OrderApiResult
import com.example.split_eat.domain.usecase.GetActiveOrdersUseCase
import com.example.split_eat.domain.usecase.GetCompletedOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class OrderViewModel @Inject constructor(
    private val getActiveOrdersUseCase: GetActiveOrdersUseCase,
    private val getCompletedOrdersUseCase: GetCompletedOrdersUseCase
) : ViewModel() {

    private val _activeOrders = MutableLiveData<List<Order>>()
    val activeOrders: LiveData<List<Order>> get() = _activeOrders

    private val _completedOrders = MutableLiveData<List<Order>>()
    val completedOrders: LiveData<List<Order>> get() = _completedOrders

    private val _messageEvent = MutableSharedFlow<String>()
    val messageEvent: SharedFlow<String> = _messageEvent

    private val _isLoading = MutableStateFlow(false)
    val isLoading : StateFlow<Boolean> = _isLoading

    fun getActiveOrders(user: String, status: String) {
        viewModelScope.launch {
            _isLoading.emit(true)
            try {
                when (val response = getActiveOrdersUseCase(user, status)) {
                    is OrderApiResult.Error -> {
                        _messageEvent.emit(response.message)
                    }
                    is OrderApiResult.Success -> {
                        _activeOrders.value = response.orders
                        Log.d("OrderViewModel", "getActiveOrders success: ${response.orders}")
                        Log.d("OrderViewModel", "Active orders after setting: ${_activeOrders.value}")
                    }
                    null -> {
                        _messageEvent.emit("Не удалось получить данные")
                    }
                }
            } catch (e: Exception) {
                _messageEvent.emit(e.message ?: "Ошибка получения активных заказов")
            } finally {
                _isLoading.emit(false)
            }
        }
    }


    fun getCompletedOrders(user: String, status: String) {
        viewModelScope.launch {
            _isLoading.emit(true)
            try {
                when (val response = getCompletedOrdersUseCase(user, status)) {
                    is OrderApiResult.Error -> {
                        _messageEvent.emit(response.message)
                    }
                    is OrderApiResult.Success -> {
                        _completedOrders.value = response.orders
                    }
                    null -> {
                        _messageEvent.emit("Не удалось получить данные")
                    }
                }
            } catch (e: Exception) {
                _messageEvent.emit(e.message ?: "Ошибка получения завершенных заказов")
            } finally {
                _isLoading.emit(false)
            }
        }
    }
}