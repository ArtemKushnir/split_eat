package com.example.split_eat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.split_eat.domain.models.AdminOrder

class AdminOrderDetailViewModel : ViewModel() {

    // Пример списка заказов администратора
    private val adminOrders = listOf(
        AdminOrder(1, "Заказ 1", "https://placekitten.com/100/100", listOf("https://placekitten.com/100/100", "https://placekitten.com/200/200"),listOf("Иван","Петр")),
        AdminOrder(2, "Заказ 2", "https://placekitten.com/200/200", listOf("https://placekitten.com/300/300", "https://placekitten.com/400/400"),listOf("Мария","Елена")),
        AdminOrder(3, "Заказ 3", "https://placekitten.com/300/300", listOf("https://placekitten.com/500/500", "https://placekitten.com/600/600"),listOf("Анна","Дмитрий")),
    )

    fun getAdminOrder(adminOrderId: Int): AdminOrder? {
        // Тут нужно сделать запрос к репозиторию, но для примера будем искать в списке
        return adminOrders.find { it.id == adminOrderId }
    }

}