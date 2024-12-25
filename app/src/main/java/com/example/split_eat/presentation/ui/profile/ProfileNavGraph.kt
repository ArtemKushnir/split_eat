package com.example.split_eat.presentation.ui.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.split_eat.presentation.ui.profile.ProfileScreen
import com.example.split_eat.presentation.ui.main.ProfileScreen

fun NavGraphBuilder.profileGraph(navController: NavController) {
    navigation(startDestination = "profile/general", route = "profile") {
        composable("profile/general") {
            ProfileScreen(onPopBack = {navController.popBackStack()},
                onLogOut = { navController.navigate("auth/greeting") {
                    popUpTo("main") { inclusive = true } }},
                navController = navController)
        }
    }
}