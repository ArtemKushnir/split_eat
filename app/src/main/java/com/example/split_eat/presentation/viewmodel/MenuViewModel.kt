package com.example.split_eat.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.split_eat.data.model.restaurant.MenuCategories
import com.example.split_eat.domain.models.CategoryApiResult
import com.example.split_eat.domain.models.Restaurant
import com.example.split_eat.domain.usecase.GetCategoriesUseCase
import com.example.split_eat.domain.usecase.GetMenuCategoriesUseCase
import com.example.split_eat.domain.usecase.GetRestaurantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getMenuCategories: GetMenuCategoriesUseCase,
): ViewModel() {

    private val _messageEvent = MutableSharedFlow<String>()
    val messageEvent = _messageEvent.asSharedFlow()

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> get() = _categories

    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> get() = _restaurants
    private var isNextPage: Boolean = true

    fun getCategories(restaurant: String) {
        viewModelScope.launch {
            try {
                when (val response = getMenuCategories(restaurant)) {
                    is CategoryApiResult.Success -> _categories.postValue(listOf("Все") + response.categories)
                    is CategoryApiResult.Error -> _messageEvent.emit(response.message)
                }
            } catch (e: Exception) {
                _messageEvent.emit(e.message ?: "Ошибка регистрации")
            }
        }
    }
}