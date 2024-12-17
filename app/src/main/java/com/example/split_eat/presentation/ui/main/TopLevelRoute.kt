package com.example.split_eat.presentation.ui.main

import androidx.compose.ui.graphics.vector.ImageVector

data class TopLevelRoute(
    val label: String,
    val route: String,
    val icon: ImageVector,
    val navigateFunction: () -> Unit
)
