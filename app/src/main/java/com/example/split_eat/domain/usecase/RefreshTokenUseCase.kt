package com.example.split_eat.domain.usecase

import com.example.split_eat.domain.repository.AuthRepository
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(refreshToken: String): Boolean {
        return authRepository.updateAccessToken(refreshToken)
    }
}