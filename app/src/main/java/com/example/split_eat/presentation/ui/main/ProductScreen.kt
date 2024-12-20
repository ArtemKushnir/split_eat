package com.example.split_eat.presentation.ui.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.split_eat.presentation.ui.theme.Tomato
import com.example.split_eat.presentation.viewmodel.MenuViewModel

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
                        Text(
                            text = restaurantName,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Tomato
                )
            )

        },
    ) { paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            )
        {
            Content(onNavCart, restaurantName)
        }
    }

}

@Composable
fun Content(onNavCart: () -> Unit, restaurantName: String) {
    val menuViewMode: MenuViewModel = hiltViewModel()

    val categories by menuViewMode.categories.observeAsState(emptyList())
    val restaurants by menuViewMode.restaurants.observeAsState(emptyList())
    
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()

    var selectedCategory by rememberSaveable { mutableStateOf<String?>(null) }
    var isInitialLoad by remember { mutableStateOf(true) }
    var currPage by remember { mutableIntStateOf(1) }
    var currCategory: String? by rememberSaveable { mutableStateOf(null) }
    var searchText by rememberSaveable { mutableStateOf("") }


    LaunchedEffect(Unit) {
        menuViewMode.getCategories(restaurantName)
    }

    LaunchedEffect(Unit) {
        menuViewMode.messageEvent.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .padding(bottom = 56.dp)
                .fillMaxSize()

        ) {
            item {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { newValue ->
                        searchText = newValue

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
                Spacer(modifier = Modifier.height(10.dp))
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

                                searchText = ""

                            },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                containerColor = Color.Gray
                            )
                        ) {
                            Text(text = category)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

        }



        Button(
            onClick = { onNavCart() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .background(Color.Transparent)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Перейти в корзину", color = Color.White)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    ProductScreen(restaurantName = "Bona", { println("") }, { println("") })
}