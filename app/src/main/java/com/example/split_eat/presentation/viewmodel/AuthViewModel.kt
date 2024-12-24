package com.example.split_eat.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.split_eat.domain.models.ApiResult
import com.example.split_eat.domain.models.UserStatus
import com.example.split_eat.domain.usecase.CheckUserStatusUseCase
import com.example.split_eat.domain.usecase.ConfirmEmailUseCase
import com.example.split_eat.domain.usecase.LoginUseCase
import com.example.split_eat.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val confirmEmailUseCase: ConfirmEmailUseCase,
    private val checkUserStatusUseCase: CheckUserStatusUseCase,
) : ViewModel() {

    private val _messageEvent = MutableSharedFlow<String>()
    val messageEvent = _messageEvent.asSharedFlow()

    private val _isLoggedIn = mutableStateOf(false)
    val isLoggedIn: State<Boolean> = _isLoggedIn

    private val _isConfirmEmail = mutableStateOf(false)
    val isConfirmEmail: State<Boolean> = _isConfirmEmail

    private val _isStaff = mutableStateOf(false)
    val isStaff: State<Boolean?> = _isStaff

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                when (val response = checkUserStatusUseCase(email)) {
                    is UserStatus.Success -> {
                        _isStaff.value = response.isStaff
                    }
                    else -> {}
                }
                when (val response = loginUseCase(email, password)) {
                    is ApiResult.Success -> _isLoggedIn.value = true
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
                    is ApiResult.Success -> _isConfirmEmail.value = true
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
                    is ApiResult.Success -> _isLoggedIn.value = true
                    is ApiResult.Error -> _messageEvent.emit(response.message)
                }
            } catch (e: Exception) {
                _messageEvent.emit(e.message ?: "Ошибка")
            }
        }
    }
}



