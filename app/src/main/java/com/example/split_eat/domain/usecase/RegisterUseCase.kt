package com.example.split_eat.domain.usecase

import com.example.split_eat.domain.models.ApiResult
import com.example.split_eat.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, username: String, password: String, confirmPassword: String): ApiResult {
        if (!validateStEmail(email)) {
            return ApiResult.Error(404, "Введена не st почта")
        }
        if (password != confirmPassword) {
            return ApiResult.Error(404, "Пароли не совпадают")
        }
        return authRepository.register(email, username, password)
    }
    private fun validateStEmail(email: String): Boolean {
        return email.startsWith("st") and email.endsWith("@student.spbu.ru")
    }
}