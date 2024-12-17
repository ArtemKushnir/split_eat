package com.example.split_eat.presentation.ui.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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

    navigation(startDestination = "main/restaurant", route = "main") {
        composable("main/restaurant") {
            MainScreen(
                onNavProfile = {navController.navigate("profile")},
                onNavCart = onNavCart,
                onNavRestaurant = onNavRestaurant,
                currentRoute = "main/restaurant",
                title = "Рестораны"
            )
        }
        composable("main/shopping_cart") {
            MainScreen(
                onNavProfile = {navController.navigate("profile")},
                onNavCart = onNavCart,
                onNavRestaurant = onNavRestaurant,
                currentRoute = "main/shopping_cart",
                title = "Корзина"
            )
        }
    }
}