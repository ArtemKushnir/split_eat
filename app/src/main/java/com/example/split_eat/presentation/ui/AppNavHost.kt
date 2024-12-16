package com.example.split_eat.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.split_eat.presentation.ui.auth.authGraph
import com.example.split_eat.presentation.ui.main.mainGraph

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "auth") {
        authGraph(navController)

        mainGraph(navController)
    }
}

