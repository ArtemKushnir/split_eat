package com.example.split_eat.presentation.ui.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.profileGraph(navController: NavController) {
    navigation(startDestination = "profile/info", route = "profile") {
        composable("profile/info") {
            ProfileScreen()
        }
    }
}