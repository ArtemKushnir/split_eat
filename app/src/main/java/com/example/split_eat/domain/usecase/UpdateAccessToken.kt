package com.example.split_eat.domain.usecase

import com.example.split_eat.domain.models.ApiResult
import com.example.split_eat.domain.repository.AuthRepository
import javax.inject.Inject

class UpdateAccessToken @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(refreshToken: String): ApiResult {
        return authRepository.updateAccessToken(refreshToken)
    }
}