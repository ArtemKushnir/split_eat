package com.example.split_eat.presentation.ui.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.split_eat.R
import com.example.split_eat.domain.models.Order
import com.example.split_eat.presentation.ui.main.user
import com.example.split_eat.presentation.ui.theme.Tomato
import com.example.split_eat.presentation.viewmodel.OrderViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, onPopBack: () -> Unit, onLogOut: () -> Unit, viewModel: OrderViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf("active") }
    val activeOrders by viewModel.activeOrders.observeAsState(emptyList())
    val completedOrders by viewModel.completedOrders.observeAsState(emptyList())
    val userName = user
    val scope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        viewModel.getActiveOrders(user =userName, status ="None")
        viewModel.getActiveOrders(user =userName, status ="True")
        viewModel.getCompletedOrders(user =userName, status ="False")

        scope.launch{
            viewModel.messageEvent.collect { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Кнопка "назад"
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier
                                .clickable {onPopBack()}
                                .padding(end = 8.dp)
                        )
//                         Аватар пользователя
                        ProfileImage()
                        // Имя пользователя
                        Text(
                            text = userName,
                            style = MaterialTheme.typography.headlineMedium,
                        )
                        Spacer(Modifier.weight(1f))


                    }
                },
                actions = {
                    IconButton(onClick = { onLogOut() }) {
                        Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "LogOut")
                    }}
//                backgroundColor = MaterialTheme.colors.primary
            )

        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // Кнопки-вкладки
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround) {
                Button(
                    onClick = { selectedTab = "active"},
                    modifier = Modifier.padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(selectedTab == "active") Tomato else MaterialTheme.colorScheme.secondary,
                        contentColor = Color.Black,
                        disabledContainerColor = MaterialTheme.colorScheme.secondary,
                        disabledContentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text("Активные заказы",  color = MaterialTheme.colorScheme.onPrimary)
                }

                Button(
                    onClick = { selectedTab = "completed"},
                    modifier = Modifier.padding(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = if(selectedTab == "active") MaterialTheme.colorScheme.secondary else Tomato,
                        contentColor = Color.Black,
                        disabledContainerColor = MaterialTheme.colorScheme.secondary,
                        disabledContentColor = MaterialTheme.colorScheme.onSecondary)
                ) {
                    Text("Завершенные заказы", color = MaterialTheme.colorScheme.onPrimary)
                }
            }

            // Список заказов (зависит от выбранной вкладки)
            if (selectedTab == "active") {
                OrderList(activeOrders)
            } else {
                OrderList(completedOrders)
            }
        }
    }
}

@Composable
fun OrderList(orders: List<Order>) {
    if (orders.isEmpty()) {
        Text("Нет заказов", modifier = Modifier.padding(16.dp))
    } else {
        LazyColumn {
            items(orders) { order ->
                OrderItem(order = order)
            }
        }
    }
}

@Composable
fun OrderItem(order: Order) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Заказ #${order.id}", style = MaterialTheme.typography.titleMedium, color = Tomato)
            Text(text = order.restaurant, style = MaterialTheme.typography.titleMedium, color = Tomato)
            Text(text = "Статус: ${order.status}", fontSize = 15.sp, color = Tomato)
            Text(text = "Стоимость заказа: ${order.total_price}", fontSize = 15.sp, color = Tomato)
            Spacer(modifier = Modifier.height(8.dp))
            ImageRow(
                images = order.products.map {it.id_product.image} ,
                captions =  order.products.map {it.id_product.name}
            )
        }
    }
}
@Composable
fun ProfileImage(){
    Image(
        painter = painterResource(id = R.drawable.photo_profile1),
        contentDescription = "Logo",
        modifier = Modifier
            .size(100.dp)
            .padding(bottom = 5.dp, top = 5.dp)
    )
}
@Composable
fun ImageRow(images: List<String>, captions: List<String>) {
    LazyRow(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        items(images.size) { index ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = images[index],
                    contentDescription = null,
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = captions[index],
                    textAlign = TextAlign.Center,
                    color = Color.White,
                )
            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun ProfileScreenPreview() {
//    val navController = rememberNavController()
//    val viewModel = FakeOrderViewModel()
//    ProfileScreen(navController = navController, { println("pop_back")}, { println("log_out") }, viewModel = viewModel)
//}
//
//class FakeOrderViewModel: OrderViewModel(FakeOrderRepository()){
//    override val activeOrders: StateFlow<List<Order>> = MutableStateFlow(
//        listOf(
//            Order(1, "Покупка продуктов", "В процессе"),
//            Order(2, "Заказ техники", "Ожидает отправки"),
//        )
//    )
//    override val completedOrders: StateFlow<List<Order>> = MutableStateFlow(
//        listOf(
//            Order(3, "Доставка цветов", "Завершен"),
//            Order(4, "Ремонт телефона", "Завершен"),
//        )
//    )
//}
//
//class FakeOrderRepository: OrderRepository {
//    override suspend fun getActiveOrders(): List<Order> = listOf(
//        Order(1, "Покупка продуктов", "В процессе"),
//        Order(2, "Заказ техники", "Ожидает отправки"),
//    )
//
//    override suspend fun getCompletedOrders(): List<Order> = listOf(
//        Order(3, "Доставка цветов", "Завершен"),
//        Order(4, "Ремонт телефона", "Завершен"),
//    )
//}