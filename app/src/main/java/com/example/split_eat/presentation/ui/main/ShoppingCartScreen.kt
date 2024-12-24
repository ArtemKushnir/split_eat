package com.example.split_eat.presentation.ui.main

import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.split_eat.presentation.viewmodel.CartViewModel
import com.example.split_eat.domain.models.CartItem
import com.example.split_eat.presentation.ui.theme.Tomato

@Composable
fun ShoppingCartScreen() {
    val viewModel: CartViewModel = hiltViewModel()
    val cartItems by viewModel.cartItems.collectAsState()
    val totalPrice by viewModel.totalPrice.collectAsState()

    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
            ) {
                // Список товаров занимает всё доступное пространство
                ItemsList(viewModel, cartItems, Modifier.weight(1f))

                // Нижняя часть с общей суммой и кнопкой
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TotalAmountText(totalPrice)
                    PlaceOrderButton()
                }
            }
        }
    )
}

@Composable
fun ItemsList(viewModel: CartViewModel, cartItems: List<CartItem>, modifier: Modifier = Modifier) {
    val groupedItems = cartItems.groupBy { it.restaurant }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp)

    ) {
        // Для каждой группы (ресторан)
        groupedItems.forEach { (restaurantName, items) ->
            item {
                // Заголовок ресторана
                Text(
                    text = restaurantName,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
            // Отображаем товары внутри ресторана
            items(items) { item ->
                CartItemRow(
                    item = item,
                    onIncrease = { viewModel.increaseQuantity(item) },
                    onDecrease = { viewModel.decreaseQuantity(item) }
                )
            }
        }
    }
}

@Composable
fun TotalAmountText(totalPrice: Double) {
    Column {
        Text(
            text = "Общая сумма:",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "${"%.2f".format(totalPrice)} ₽",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun PlaceOrderButton() {
    val cartViewModel: CartViewModel = hiltViewModel()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        cartViewModel.messageEvent.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Button(
        onClick = { cartViewModel.send_cart() },
        modifier = Modifier.height(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Tomato,
            contentColor = Color.Black
        )
    ) {
        Text("Оформить заказ")
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Column(
        modifier = Modifier.padding(end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.image,
                contentDescription = "Картинка товара",
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(30.dp)),
                contentScale = ContentScale.Fit
            )

            // Новый столбец для названия и цены
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = item.name, // Название продукта
                    //style = MaterialTheme.typography.bodyLarge,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "${item.price} ₽", // Цена продукта
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            Row {
                IconButton(onClick = onDecrease) { // Уменьшить количество
                    Icon(Icons.Default.Remove, contentDescription = "Уменьшить")
                }
                Text(
                    text = "${item.quantity}",
                    modifier = Modifier.padding(top = 12.dp, start = 8.dp, end = 8.dp)
                ) // Количество
                IconButton(onClick = onIncrease) { // Увеличить количество
                    Icon(Icons.Default.Add, contentDescription = "Увеличить")
                }
            }
        }
    }
}
