package com.example.split_eat.presentation.ui.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.split_eat.domain.models.Restaurant
import com.example.split_eat.presentation.viewmodel.RestaurantViewModel
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager


@Composable
fun RestaurantScreen() {
    val restaurantViewModel: RestaurantViewModel = hiltViewModel()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val categories by restaurantViewModel.categories.observeAsState(emptyList())
    val restaurants by restaurantViewModel.restaurants.observeAsState(emptyList())
    val isLoading by restaurantViewModel.isLoading.observeAsState(false)
    val listState = rememberLazyListState()

    var selectedCategory by rememberSaveable { mutableStateOf<String?>(null) }
    var isInitialLoad by remember { mutableStateOf(true) }
    var currPage by remember { mutableIntStateOf(1) }
    var currCategory: String? by rememberSaveable { mutableStateOf(null) }

    var searchText by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        restaurantViewModel.messageEvent.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        restaurantViewModel.getCategories()
        restaurantViewModel.getRestaurants(page = currPage)
        isInitialLoad = false
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .collect { layoutInfo ->
                val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                if (lastVisibleItemIndex == currPage * 5 && !isLoading && !isInitialLoad) {
                    currPage += 1
                    restaurantViewModel.getRestaurants(page = currPage, category = currCategory)
                }
            }
    }

    val handleOutsideClick = Modifier.pointerInput(Unit) {
        detectTapGestures(
            onTap = {
                focusManager.clearFocus()
                if (searchText != "") {
                    currCategory = null
                    currPage = 1
                    restaurantViewModel.reloadRestaurants()
                    restaurantViewModel.getRestaurants(currPage, currCategory)
                }
                searchText = ""

            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .then(handleOutsideClick),
        state = listState
    ) {
        item {
            OutlinedTextField(
                value = searchText,
                onValueChange = { newValue ->
                    searchText = newValue
                    selectedCategory = null
                    currPage = 1
                    restaurantViewModel.reloadRestaurants()
                    restaurantViewModel.getRestaurants(page = currPage, search = searchText.ifEmpty { null })
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
            Spacer(modifier = Modifier.height(16.dp))

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
                            restaurantViewModel.reloadRestaurants()
                            currPage = 1
                            searchText = ""
                            currCategory = if (category == "Все") null else category
                            restaurantViewModel.getRestaurants(currPage, currCategory)
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

            Spacer(modifier = Modifier.height(16.dp))
        }

        items(restaurants) { restaurant ->
            RestaurantItem(restaurant = restaurant)
            Spacer(modifier = Modifier.height(10.dp))
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
}

@Composable
fun RestaurantItem(restaurant: Restaurant) {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth(),
        thickness = 1.dp,
        color = Color.Gray
    )
    Spacer(modifier = Modifier.height(10.dp))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = restaurant.logo,
            contentDescription = "Лого ресторана",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = restaurant.name,
                color = Color.Black,
                fontSize = 15.sp
            )
            Text(
                text = restaurant.categories.joinToString(separator = ", "),
                color = Color.Black,
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Text(text = "Бесплатная доставка от ${restaurant.free_shipping_price}₽", fontSize = 12.sp)

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun RestaurantsPreview() {
    RestaurantScreen()
}
