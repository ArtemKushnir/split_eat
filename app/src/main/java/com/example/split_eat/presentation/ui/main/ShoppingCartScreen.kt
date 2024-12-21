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
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.split_eat.presentation.viewmodel.CartViewModel
import com.example.split_eat.domain.models.CartItem
import com.example.split_eat.presentation.ui.theme.Tomato

@Composable
fun ShoppingCartScreen() {
    val viewModel: CartViewModel = hiltViewModel()
    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
            ) {
                ItemsList(viewModel)
                Spacer(modifier = Modifier.weight(1f))
                TotalAmountText(viewModel)
                PlaceOrderButton()
            }
        }
    )
}

@Composable
fun ItemsList(viewModel: CartViewModel) {
    val cartItems = viewModel.cartItems

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
    ) {
        items(cartItems) { item ->
            CartItemRow(
                item = item,
                onIncrease = { viewModel.increaseQuantity(item) },
                onDecrease = { viewModel.decreaseQuantity(item) }
            )
        }
    }
}

@Composable
fun TotalAmountText(viewModel: CartViewModel) {
    val totalPrice = viewModel.getTotalPrice()

    Text(
        text = "Общая сумма: ${"%.2f".format(totalPrice)} ₽",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 16.dp)
    )
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
        onClick = {cartViewModel.send_cart()},
        modifier = Modifier.fillMaxWidth(),
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
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Text(text = item.name, modifier = Modifier.weight(1f))  // наименование товара
            Text(
                text = "${item.price} ₽",
                modifier = Modifier.padding(horizontal = 8.dp)
            )  // цена товара
            Row {
                IconButton(onClick = onDecrease) {  // уменьшить количество
                    Icon(Icons.Default.Remove, contentDescription = "Уменьшить")
                }
                Text(
                    text = "${item.quantity}",
                    modifier = Modifier.padding(top = 12.dp, start = 8.dp, end = 8.dp)
                )  // количество
                IconButton(onClick = onIncrease) {  // увеличить количество
                    Icon(Icons.Default.Add, contentDescription = "Увеличить")
                }
            }
        }
    }
}