package com.example.split_eat.presentation.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.split_eat.presentation.ui.theme.Tomato

@Composable
fun BottomNavigationBar(
    onNavRestaurant: () -> Unit,
    onNavCart: () -> Unit,
    currentRoute: String
) {
    val topLevelRoutes = listOf(
        TopLevelRoute("Рестораны", "main/restaurant", Icons.Filled.Restaurant, onNavRestaurant),
        TopLevelRoute("Корзина", "main/shopping_cart", Icons.Filled.ShoppingCart, onNavCart)
    )
    NavigationBar(
        containerColor = Tomato
    ) {
        topLevelRoutes.forEach { topLevelRoute ->
            NavigationBarItem(
                label = {Text(topLevelRoute.label)},
                selected = currentRoute == topLevelRoute.route,
                onClick = {
                    if (currentRoute != topLevelRoute.route) {
                        topLevelRoute.navigateFunction()
                    }
                },
                icon = { Icon(topLevelRoute.icon, topLevelRoute.label) })
        }
    }
}