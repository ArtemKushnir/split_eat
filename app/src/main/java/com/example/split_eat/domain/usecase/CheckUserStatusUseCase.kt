package com.example.split_eat.domain.usecase

import com.example.split_eat.domain.models.UserStatus
import com.example.split_eat.domain.repository.AuthRepository
import javax.inject.Inject

class CheckUserStatusUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String): UserStatus {
        return authRepository.checkUserStatus(email)
    }
}