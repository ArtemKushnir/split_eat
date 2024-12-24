package com.example.split_eat.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.split_eat.domain.models.CategoryApiResult
import com.example.split_eat.domain.models.Restaurant
import com.example.split_eat.domain.models.RestaurantApiResult
import com.example.split_eat.domain.usecase.GetCategoriesUseCase
import com.example.split_eat.domain.usecase.GetRestaurantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getRestaurantsUseCase: GetRestaurantsUseCase
): ViewModel(){

    private val _isAuth = MutableLiveData(false)
    val isAuth: LiveData<Boolean> get() = _isAuth

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _messageEvent = MutableSharedFlow<String>()
    val messageEvent = _messageEvent.asSharedFlow()

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> get() = _categories

    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> get() = _restaurants
    private var isNextPage: Boolean = true


    fun getCategories() {
        viewModelScope.launch {
            try {
                when (val response = getCategoriesUseCase()) {
                    null -> _isAuth.value = false
                    is CategoryApiResult.Success -> _categories.postValue(listOf("Все") + response.categories)
                    is CategoryApiResult.Error -> _messageEvent.emit(response.message)
                }
            } catch (e: Exception) {
                _messageEvent.emit(e.message ?: "Ошибка регистрации")
            }
        }
    }
    fun getRestaurants(page: Int? = null, category: String? = null, search: String? = null) {
        if (!isNextPage || _isLoading.value == true) return

        viewModelScope.launch {
            try {
                _isLoading.value = true
                when (val response = getRestaurantsUseCase(page, category, search)) {
                    null -> _isAuth.value = false
                    is RestaurantApiResult.Success -> {
                        if (response.next == null) isNextPage = false
                        _restaurants.postValue(_restaurants.value.orEmpty() + response.restaurants)
                    }

                    is RestaurantApiResult.Error -> {
                        _messageEvent.emit(response.message)
                    }
                }
            } catch (e: Exception) {
                _messageEvent.emit(e.message ?: "Ошибка загрузки ресторанов")
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun reloadRestaurants() {
        viewModelScope.launch {
            _restaurants.postValue(emptyList())
            isNextPage = true
            _isLoading.value = false
        }
    }

}