package com.example.split_eat.presentation.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.split_eat.presentation.ui.theme.Tomato
import com.example.split_eat.presentation.viewmodel.RestaurantViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavProfile: () -> Unit,
    onNavRestaurant: () -> Unit,
    onNavCart: () -> Unit,
    currentRoute: String,
    title: String,
    navController: NavController? = null
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = { Text(title, Modifier.padding(start = 20.dp)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Tomato
                ),
                actions = {
                    IconButton(
                        modifier = Modifier.padding(end = 20.dp),
                        onClick = { onNavProfile() }
                    ) {
                        Icon(
                            Icons.Filled.Person,
                            contentDescription = "Navigate to profile",
                            modifier = Modifier
                                .size(40.dp)
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(onNavRestaurant = onNavRestaurant,
                onNavCart = onNavCart, currentRoute = currentRoute)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (currentRoute) {
                "main/restaurant" -> RestaurantScreen(navController = navController)
                "main/shopping_cart" -> ShoppingCartScreen()
            }
        }
    }
}
