package com.example.split_eat.presentation.ui.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(startDestination = "auth/greeting", route = "auth") {
        composable("auth/greeting") {
            GreetingScreen(
                onNavigateLogin = {navController.navigate("auth/login")},
                onNavigateRegister = {navController.navigate("auth/register")}
            )
        }
        composable("auth/login") {
            LoginScreen(
                onPopBack = {navController.popBackStack()},
                onLogin = {navController.navigate("main") {
                    popUpTo("auth") { inclusive = true } }
                }
            )
        }
        composable("auth/register") {
            RegisterScreen(
                onPopBack = {navController.popBackStack()},
                onNavigate = { email: String -> navController.navigate("auth/confirm_email/$email")}
                )
        }
        composable(
            "auth/confirm_email/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            if (email != null) {
                EmailConfirmationDialog(
                    email = email,
                    onNavigate = {navController.navigate("main") {
                        popUpTo("auth") { inclusive = true }
                    } },
                    onPopBack = { navController.popBackStack() }
                )
            }
        }
    }
}

