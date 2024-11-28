package com.example.split_eat.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.example.split_eat.utils.Resource
import com.example.split_eat.presentation.ui.auth.LoginScreen
import com.example.split_eat.presentation.ui.auth.RegisterScreen
import com.example.split_eat.presentation.ui.auth.GreetingScreen
import com.example.split_eat.presentation.viewmodel.AuthUiState
import com.example.split_eat.presentation.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val uiState = authViewModel.uiState.collectAsState().value
            val authState = authViewModel.authState.collectAsState().value

            when (uiState) {
                is AuthUiState.Welcome -> GreetingScreen()
                is AuthUiState.Login -> LoginScreen()
                is AuthUiState.Register -> RegisterScreen()
            }

            LaunchedEffect(authState) {
                when (authState) {
                    is Resource.Loading -> {
                        // Покажите индикатор загрузки, если необходимо
                    }
                    is Resource.Success -> {
                        Toast.makeText(applicationContext, "Успех", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        Toast.makeText(applicationContext, authState.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> { }
                }
            }
        }
    }
}
