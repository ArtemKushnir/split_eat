package com.example.split_eat.presentation.ui.admin

import AdminProfileScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation

fun NavGraphBuilder.adminGraph(navController: NavController) {
    val onNavOrderDetail = { adminOrderId: Int ->
        navController.navigate("admin/orderdetail/$adminOrderId") {
            popUpTo("admin/orders") { saveState = true } // Убрал это
            launchSingleTop = true
            restoreState = true // Убрал это
        }}
    val onNavAdminProfile = {
        navController.navigate("admin/profile") {
            popUpTo("admin/orders") { saveState = true }
            launchSingleTop = true
            restoreState = true
        }}

    navigation(startDestination = "admin/orders", route = "admin") {
        composable(route = "admin/orders") {
            val viewModel = AdminOrdersViewModel()
            AdminOrdersScreen(
                navController = navController,
                viewModel = viewModel,
                onNavOrderDetail = onNavOrderDetail,
                onNavAdminProfile = onNavAdminProfile,
                title = "Окно с заказами"
            )
        }
        composable(
            route = "admin/orderdetail/{adminOrderId}",
            arguments = listOf(navArgument("adminOrderId") { type = NavType.IntType })
        ) { backStackEntry ->
            val viewModel = AdminOrderDetailViewModel()
            val adminOrderId = backStackEntry.arguments?.getInt("adminOrderId") ?: 0
            AdminOrderDetailScreen(navController = navController, adminOrderId = adminOrderId, viewModel = viewModel)
        }
        composable(route = "admin/profile") {
            AdminProfileScreen(
                navController = navController,
                onPopBack = {navController.popBackStack()},
                onLogOut = { navController.navigate("auth/greeting") }
            )
        }
    }
}