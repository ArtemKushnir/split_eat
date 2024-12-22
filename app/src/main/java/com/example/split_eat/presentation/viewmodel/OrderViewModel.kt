package com.example.split_eat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.split_eat.domain.models.Order
import com.example.split_eat.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class OrderViewModel @Inject constructor(private val orderRepository: OrderRepository) : ViewModel() {

    private val _activeOrders = MutableStateFlow<List<Order>>(emptyList())
    open val activeOrders: StateFlow<List<Order>> = _activeOrders

    private val _completedOrders = MutableStateFlow<List<Order>>(emptyList())
    open val completedOrders: StateFlow<List<Order>> = _completedOrders

    init {
        loadActiveOrders()
        loadCompletedOrders()
    }

    private fun loadActiveOrders() {
        viewModelScope.launch {
            _activeOrders.value = orderRepository.getActiveOrders()
        }
    }

    private fun loadCompletedOrders() {
        viewModelScope.launch {
            _completedOrders.value = orderRepository.getCompletedOrders()
        }
    }
}