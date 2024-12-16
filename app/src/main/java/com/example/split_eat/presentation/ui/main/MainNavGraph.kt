package com.example.split_eat.presentation.ui.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(startDestination = "main/after_auth", route = "main") {
        composable("main/after_auth") {
            AfterAuth()
        }
    }
}