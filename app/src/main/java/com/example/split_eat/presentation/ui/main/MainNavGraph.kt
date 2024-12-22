package com.example.split_eat.presentation.ui.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation

fun NavGraphBuilder.mainGraph(navController: NavController) {
    val onNavCart = {
        navController.navigate("main/shopping_cart") {
            popUpTo("main") { saveState = true }
            launchSingleTop = true
            restoreState = true
        }}
    val onNavRestaurant = {
        navController.navigate("main/restaurant") {
            popUpTo("main") { saveState = true }
            launchSingleTop = true
            restoreState = true
        }}
    val onNavProfile = {
        navController.navigate("main/profile") {
            popUpTo("main") { saveState = true }
            launchSingleTop = true
            restoreState = true
        }}

    navigation(startDestination = "main/restaurant", route = "main") {
        composable("main/restaurant") {
            MainScreen(
                onNavProfile = {navController.navigate("profile")},
                onNavCart = onNavCart,
                onNavRestaurant = onNavRestaurant,
                currentRoute = "main/restaurant",
                title = "Рестораны",
                navController = navController
            )
        }
        composable("product/{restaurantName}") { backStackEntry ->
            backStackEntry.arguments?.getString("restaurantName")
                ?.let { ProductScreen(
                    restaurantName = it,
                    onBackStack = {navController.popBackStack()},
                    onNavCart = { navController.navigate("main/shopping_cart") },
                    )}
        }
        composable("main/shopping_cart") {
            MainScreen(
                onNavProfile = {navController.navigate("profile")},
                onNavCart = onNavCart,
                onNavRestaurant = onNavRestaurant,
                currentRoute = "main/shopping_cart",
                title = "Корзина",
            )
        }
        composable("main/profile") {
            MainScreen(
                onNavProfile = {navController.navigate("profile")},
                onNavCart = onNavCart,
                onNavRestaurant = onNavProfile,
                currentRoute = "main/profile",
                title = "Профиль"
            )
        }
    }
}