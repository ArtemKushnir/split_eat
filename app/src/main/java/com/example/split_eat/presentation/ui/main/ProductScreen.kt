package com.example.split_eat.presentation.ui.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.split_eat.R
import com.example.split_eat.domain.models.Product
import com.example.split_eat.domain.models.Restaurant
import com.example.split_eat.presentation.ui.theme.Gainsboro
import com.example.split_eat.presentation.ui.theme.LightGrey
import com.example.split_eat.presentation.ui.theme.Tomato
import com.example.split_eat.presentation.viewmodel.CartViewModel
import com.example.split_eat.presentation.viewmodel.MenuViewModel
import com.example.split_eat.presentation.viewmodel.RestaurantViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(restaurantName: String, onBackStack: () -> Unit, onNavCart: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = restaurantName)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Tomato
                )
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            Content(onNavCart, restaurantName)
        }
    }
}

@Composable
fun Content(onNavCart: () -> Unit, restaurantName: String) {
    val menuViewModel: MenuViewModel = hiltViewModel()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val categories by menuViewModel.categories.observeAsState(emptyList())
    val products by menuViewModel.products.observeAsState(emptyList())
    val isLoading by menuViewModel.isLoading.observeAsState(false)
    var selectedCategory by rememberSaveable { mutableStateOf<String?>(null) }
    var isInitialLoad by rememberSaveable { mutableStateOf(true) }
    var currPage by remember { mutableIntStateOf(1) }
    var currCategory: String? by rememberSaveable { mutableStateOf(null) }
    var searchText by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (isInitialLoad) {
            menuViewModel.getCategories(restaurantName)
            menuViewModel.getProducts(restaurant = restaurantName)
            isInitialLoad = false
        }
    }
    LaunchedEffect(Unit) {
        menuViewModel.messageEvent.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    val handleOutsideClick = Modifier.pointerInput(Unit) {
        detectTapGestures(
            onTap = {
                focusManager.clearFocus()
                if (searchText != "") {
                    currCategory = null
                    currPage = 1
                    menuViewModel.reloadProducts()
                    menuViewModel.getProducts(restaurantName)
                }
                searchText = ""

            }
        )
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(bottom = 80.dp)
                .then(handleOutsideClick),
            ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { newValue ->
                    searchText = newValue
                    selectedCategory = null
                    currPage = 1
                    menuViewModel.reloadProducts()
                    menuViewModel.getProducts(
                        restaurant = restaurantName,
                        search = searchText.ifEmpty { null }
                    )
                },
                label = { Text("Поиск") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedIndicatorColor = Color.DarkGray,
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    Button(
                        onClick = {
                            selectedCategory = category
                            menuViewModel.reloadProducts()
                            currPage = 1
                            searchText = ""
                            currCategory = if (category == "Все") null else category
                            menuViewModel.getProducts(restaurantName, currCategory)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedCategory == category) Color.LightGray else Color.Gray,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = category)
                    }
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(8.dp),
                content = {
                    items(products) { product ->
                        ProductItem(menuViewModel, product, restaurantName)
                    }

                    if (isLoading) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    }
                }
            )
        }

        Button(
            onClick = { onNavCart() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.DarkGray
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Перейти в корзину")
        }
    }
}

@Composable
fun ProductItem(menuViewModel: MenuViewModel, product: Product, nameRestaurant: String) {
    val unitsOfMeasurement = mapOf(
        "g" to " г",
        "ml" to " мл",
        "kg" to " кг",
        "l" to " л"
    )
    Column(
        modifier = Modifier
            .padding(3.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .fillMaxWidth()
            .background(LightGrey)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.image)
                .placeholder(R.drawable.food_placeholder)
                .error(R.drawable.error_product)
                .build(),
            contentDescription = "Изображение продукта",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(10.dp))
                .padding(top = 8.dp, start = 8.dp, end = 8.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${product.price} ₽",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier.padding(horizontal = 8.dp)

        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = product.name,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = (
                        product.weight.toString() + unitsOfMeasurement[product.weight_unit]
                        ),
                color = Color.Gray,
                fontSize = 12.sp
            )
            Text(
                text = (product.calories?.toString() + " ккал"),
                fontSize = 12.sp,
                color = Color.Gray,
            )
        }
        Spacer(modifier = Modifier.height(5.dp))

        Button(
            onClick = { menuViewModel.addProductInCart(product, nameRestaurant) },
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 5.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Добавить в корзину",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    ProductScreen(restaurantName = "Bona", { println("") }, { println("") })
}