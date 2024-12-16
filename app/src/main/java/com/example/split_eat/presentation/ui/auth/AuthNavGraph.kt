package com.example.split_eat.presentation.ui.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.split_eat.presentation.ui.main.AfterAuth

@Composable
fun AuthNavGraph() {
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = "greeting") {
        composable("greeting") {
            GreetingScreen(navController)
        }
        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable(
            "confirm_email/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType})
            ) { backStackEntry -> val email = backStackEntry.arguments?.getString("email")
            if (email != null) {
                EmailConfirmationDialog(email = email, navController)
            }
        }
        composable("main_content") {
            AfterAuth()
        }
    }
}

