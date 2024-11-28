package com.example.split_eat.domain.usecase

import com.example.split_eat.domain.repository.AuthRepository
import javax.inject.Inject

class ConfirmEmailUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, code: String): Boolean {
        return authRepository.confirmEmail(email, code)
    }
}