package com.example.split_eat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.split_eat.domain.models.ApiResult
import com.example.split_eat.domain.usecase.ConfirmEmailUseCase
import com.example.split_eat.domain.usecase.LoginUseCase
import com.example.split_eat.domain.usecase.RegisterUseCase
import com.example.split_eat.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val confirmEmailUseCase: ConfirmEmailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Welcome)
    val uiState: StateFlow<AuthUiState> = _uiState

    private val _authState = MutableStateFlow<Resource<Unit>>(Resource.Idle)
    val authState: StateFlow<Resource<Unit>> = _authState


    fun navigateToLogin() {
        _uiState.value = AuthUiState.Login
    }

    fun navigateToRegister() {
        _uiState.value = AuthUiState.Register
    }

    fun navigateToWelcome() {
        _uiState.value = AuthUiState.Welcome
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = Resource.Loading
            try {
                when (val response = loginUseCase(email, password)) {
                    is ApiResult.Success -> _authState.value = Resource.Success(Unit)
                    is ApiResult.Error -> _authState.value = Resource.Error(response.message)
                }
            } catch (e: Exception) {
                _authState.value = Resource.Error(e.message ?: "Ошибка входа")
            }
        }
    }

    fun register(email: String, username: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            _authState.value = Resource.Loading
            try {
                when (val response = registerUseCase(email, username, password, confirmPassword)) {
                    is ApiResult.Success -> _uiState.value = AuthUiState.ConfirmEmail(email)
                    is ApiResult.Error -> _authState.value = Resource.Error(response.message)
                }
            } catch (e: Exception) {
                _authState.value = Resource.Error(e.message ?: "Ошибка регистрации")

            }
        }
    }
    fun confirmEmail(email: String, code: String) {
        viewModelScope.launch {
            _authState.value = Resource.Loading
            try {
                when (val response = confirmEmailUseCase(email, code)) {
                    is ApiResult.Success -> _authState.value = Resource.Success(Unit)
                    is ApiResult.Error -> _authState.value = Resource.Error(response.message)
                }
            } catch (e: Exception) {
                _authState.value = Resource.Error(e.message ?: "Ошибка")
            }
        }
    }
}

sealed class AuthUiState {
    data object Welcome : AuthUiState()
    data object Login : AuthUiState()
    data object Register : AuthUiState()
    data class ConfirmEmail(var email: String): AuthUiState()
}
