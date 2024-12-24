package com.example.split_eat.presentation.ui.admin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.split_eat.domain.models.AdminOrder
import com.example.split_eat.presentation.ui.main.ImageRow
import com.example.split_eat.presentation.ui.theme.Tomato

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminOrdersScreen(navController: NavController, onNavAdminProfile: () -> Unit, title: String, viewModel: AdminOrdersViewModel, onNavOrderDetail: (Int) -> Unit) {
    val adminOrders = viewModel.adminOrders // Получаем список заказов из ViewModel

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
                        onClick = { onNavAdminProfile() }
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
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(adminOrders) { adminOrder ->
                AdminOrderItemCard(adminOrder = adminOrder, onClick = {
                    navController.navigate("admin/orderdetail/${adminOrder.id}") // Переход к деталям
                })
            }
        }
    }
}


@Composable
fun AdminOrderItemCard(adminOrder: AdminOrder, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Заказ #${adminOrder.id}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            ImageRow(images = adminOrder.images, captions = adminOrder.images)
        }
    }
}


// ViewModel (пример)
open class AdminOrdersViewModel {
    open val adminOrders = listOf(
        AdminOrder(1, "Заказ 1", "https://placekitten.com/100/100", listOf("https://placekitten.com/100/100", "https://placekitten.com/200/200")),
        AdminOrder(2, "Заказ 2", "https://placekitten.com/200/200", listOf("https://placekitten.com/300/300", "https://placekitten.com/400/400")),
        AdminOrder(3, "Заказ 3", "https://placekitten.com/300/300", listOf("https://placekitten.com/500/500", "https://placekitten.com/600/600")),
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AdminOrdersScreenPreview() {
    val navController = rememberNavController()
    AdminOrdersScreen(
        navController = navController,
        viewModel = FakeAdminOrdersViewModel(),
        onNavOrderDetail = { _ -> },
        onNavAdminProfile = { println("") },
        title = { println("") }.toString() // Add a stub for onNavOrderDetail
    )
}

class FakeAdminOrdersViewModel: AdminOrdersViewModel(){
    override val adminOrders: List<AdminOrder> = listOf(
        AdminOrder(1, "Заказ 1", "https://placekitten.com/100/100", listOf("https://placekitten.com/100/100", "https://placekitten.com/200/200"), listOf("Иван","Петр")),
        AdminOrder(2, "Заказ 2", "https://placekitten.com/200/200", listOf("https://placekitten.com/300/300", "https://placekitten.com/400/400"),listOf("Мария","Елена")),
        AdminOrder(3, "Заказ 3", "https://placekitten.com/300/300", listOf("https://placekitten.com/500/500", "https://placekitten.com/600/600"), listOf("Анна","Дмитрий")),
    )
}


