package com.example.split_eat.presentation.viewmodel

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.split_eat.domain.models.ApiResult
import com.example.split_eat.domain.usecase.ConfirmEmailUseCase
import com.example.split_eat.domain.usecase.LoginUseCase
import com.example.split_eat.domain.usecase.RegisterUseCase
import com.example.split_eat.utils.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val confirmEmailUseCase: ConfirmEmailUseCase
) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<String>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _messageEvent = MutableSharedFlow<String>()
    val messageEvent = _messageEvent.asSharedFlow()


    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                when (val response = loginUseCase(email, password)) {
                    is ApiResult.Success -> _navigationEvent.emit(AuthUiState.MAIN_CONTENT.name)
                    is ApiResult.Error -> _messageEvent.emit(response.message)
                }
            } catch (e: Exception) {
                _messageEvent.emit(e.message ?: "Ошибка входа")
            }
        }
    }

    fun register(email: String, username: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            try {
                when (val response = registerUseCase(email, username, password, confirmPassword)) {
                    is ApiResult.Success -> _navigationEvent.emit(AuthUiState.CONFIRM_EMAIL.name + "/$email")
                    is ApiResult.Error -> _messageEvent.emit(response.message)
                }
            } catch (e: Exception) {
                _messageEvent.emit(e.message ?: "Ошибка регистрации")

            }
        }
    }
    fun confirmEmail(email: String, code: String) {
        viewModelScope.launch {
            try {
                when (val response = confirmEmailUseCase(email, code)) {
                    is ApiResult.Success -> _navigationEvent.emit(AuthUiState.MAIN_CONTENT.name)
                    is ApiResult.Error -> _messageEvent.emit(response.message)
                }
            } catch (e: Exception) {
                _messageEvent.emit(e.message ?: "Ошибка")
            }
        }
    }
}



