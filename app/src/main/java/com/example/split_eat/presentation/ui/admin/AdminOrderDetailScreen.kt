package com.example.split_eat.presentation.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.split_eat.domain.models.AdminOrder
import com.example.split_eat.presentation.ui.main.ImageRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminOrderDetailScreen(navController: NavController, adminOrderId: Int, viewModel: com.example.split_eat.presentation.ui.admin.AdminOrderDetailViewModel) {
    val adminOrder = viewModel.getAdminOrder(adminOrderId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Заказ #${adminOrder?.id ?: ""}") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (adminOrder == null) {
            Text("Заказ не найден")
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                items(adminOrder.participants){ participant ->
                    AdminParticipantCard(name = participant, images = adminOrder.images)
                }
            }

        }
    }
}


@Composable
fun AdminParticipantCard(name:String, images: List<String>){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Отдать")
            }
            Spacer(modifier = Modifier.height(8.dp))
            ImageRow(images = images, captions = images)
        }
    }
}
// ViewModel (пример)
open class AdminOrderDetailViewModel {
    open fun getAdminOrder(adminOrderId: Int): AdminOrder? {
        val adminOrders = listOf(
            AdminOrder(1, "Заказ 1", "https://placekitten.com/100/100", listOf("https://placekitten.com/100/100", "https://placekitten.com/200/200"),listOf("Иван","Петр")),
            AdminOrder(2, "Заказ 2", "https://placekitten.com/200/200", listOf("https://placekitten.com/300/300", "https://placekitten.com/400/400"),listOf("Мария","Елена")),
            AdminOrder(3, "Заказ 3", "https://placekitten.com/300/300", listOf("https://placekitten.com/500/500", "https://placekitten.com/600/600"),listOf("Анна","Дмитрий")),
        )
        return adminOrders.find { it.id == adminOrderId }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AdminOrderDetailScreenPreview() {
    val navController = rememberNavController()
    val viewModel = FakeAdminOrderDetailViewModel()
    AdminOrderDetailScreen(
        navController = navController,
        adminOrderId = 1,
        viewModel = viewModel
    )
}

class FakeAdminOrderDetailViewModel: AdminOrderDetailViewModel(){
    override fun getAdminOrder(adminOrderId: Int): AdminOrder? {
        val adminOrders = listOf(
            AdminOrder(1, "Заказ 1", "https://placekitten.com/100/100", listOf("https://placekitten.com/100/100", "https://placekitten.com/200/200"),listOf("Иван","Петр")),
            AdminOrder(2, "Заказ 2", "https://placekitten.com/200/200", listOf("https://placekitten.com/300/300", "https://placekitten.com/400/400"),listOf("Мария","Елена")),
            AdminOrder(3, "Заказ 3", "https://placekitten.com/300/300", listOf("https://placekitten.com/500/500", "https://placekitten.com/600/600"),listOf("Анна","Дмитрий")),
        )
        return adminOrders.find { it.id == adminOrderId }
    }
}