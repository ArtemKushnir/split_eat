package com.example.split_eat.domain.usecase

import com.example.split_eat.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Boolean {
        return authRepository.login(email, password)
    }
}