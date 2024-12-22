package com.example.split_eat.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.split_eat.domain.models.CategoryApiResult
import com.example.split_eat.domain.models.Product
import com.example.split_eat.domain.models.ProductApiResult
import com.example.split_eat.domain.models.Restaurant
import com.example.split_eat.domain.usecase.GetMenuCategoriesUseCase
import com.example.split_eat.domain.usecase.GetMenuRestaurantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getMenuCategories: GetMenuCategoriesUseCase,
    private val getMenuRestaurant: GetMenuRestaurantUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading
    private var isNextPage: Boolean = true

    private val _messageEvent = MutableSharedFlow<String>()
    val messageEvent = _messageEvent.asSharedFlow()

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> get() = _categories

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products


    fun getCategories(restaurant: String) {
        viewModelScope.launch {
            try {
                when (val response = getMenuCategories(restaurant)) {
                    is CategoryApiResult.Success -> {
                        _categories.postValue(listOf("Все") + response.categories)
                    }
                    is CategoryApiResult.Error -> {
                        _messageEvent.emit(response.message)
                    }

                }
            } catch (e: Exception) {
                _messageEvent.emit(e.message ?: "Ошибка загрузки категорий")
            }
        }
    }

    fun getProducts(restaurant: String, category: String? = null, search: String? = null) {
        viewModelScope.launch {
            try {
                    when (val response = getMenuRestaurant(restaurant, category, search)) {
                        is ProductApiResult.Success -> {
                            _products.postValue(response.products)
                        }

                        is ProductApiResult.Error -> {
                            _messageEvent.emit(response.message)
                        }
                    }
                }
            catch (e: Exception) {
                _messageEvent.emit(e.message ?: "Ошибка загрузки продуктов")
            }
        }
    }
    fun reloadProducts() {
        viewModelScope.launch {
            _products.postValue(emptyList())
            isNextPage = true
            _isLoading.value = false
        }
    }

}
