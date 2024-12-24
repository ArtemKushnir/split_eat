package com.example.split_eat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.split_eat.domain.models.AdminOrder
import com.example.split_eat.domain.repository.AdminOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AdminOrdersViewModel @Inject constructor(private val orderRepository: AdminOrderRepository) : ViewModel() {

    private val _activeAdminOrders = MutableStateFlow<List<AdminOrder>>(emptyList())
    open val activeAdminOrders: StateFlow<List<AdminOrder>> = _activeAdminOrders

    private val _completedAdminOrders = MutableStateFlow<List<AdminOrder>>(emptyList())
    open val completedAdminOrders: StateFlow<List<AdminOrder>> = _completedAdminOrders

    init {
        loadActiveAdminOrders()
        loadCompletedAdminOrders()
    }

    private fun loadActiveAdminOrders() {
        viewModelScope.launch {
            _activeAdminOrders.value = orderRepository.getActiveAdminOrders()
        }
    }

    private fun loadCompletedAdminOrders() {
        viewModelScope.launch {
            _completedAdminOrders.value = orderRepository.getCompletedAdminOrders()
        }
    }
}