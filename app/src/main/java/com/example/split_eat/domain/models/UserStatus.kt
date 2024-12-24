package com.example.split_eat.domain.models

sealed class UserStatus {
    data class Success(val isStaff: Boolean) : UserStatus()
    data object Error : UserStatus()
}