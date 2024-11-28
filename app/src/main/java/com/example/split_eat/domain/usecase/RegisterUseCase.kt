package com.example.split_eat.domain.usecase

import com.example.split_eat.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, username: String, password: String): Boolean {
        return authRepository.register(email, username, password)
    }
}