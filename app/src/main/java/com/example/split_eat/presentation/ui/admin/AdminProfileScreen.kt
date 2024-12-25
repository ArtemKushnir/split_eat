import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.split_eat.R
import com.example.split_eat.presentation.ui.profile.ProfileImage

data class Order(val orderNumber: String, val amount: Double)
data class Photo(val imageResId: Int, val caption: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminProfileScreen(
    navController: NavController,
    onPopBack: () -> Unit,
    onLogOut: () -> Unit,
) {
    val orders = listOf(
        Order("12345", 1500.0),
        Order("67890", 2200.0),
        Order("13579", 1000.0),
        Order("24680", 3000.0)
    )
    val photos = listOf(
        Photo(R.drawable.photo_profile1, "Пейзаж 1"),
        Photo(R.drawable.photo_profile1, "Пейзаж 2"),
        Photo(R.drawable.photo_profile1, "Город"),
        Photo(R.drawable.photo_profile1, "Море")
    )
    val userName = "John Doe"
    Column(modifier = Modifier.fillMaxSize()) {
        // Верхняя панель
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
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Список завершенных заказов
            Text(text = "Завершенные заказы", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(bottom = 8.dp))
            LazyColumn(modifier = Modifier.weight(1f), contentPadding = PaddingValues(bottom = 16.dp)) {
                items(orders) { order ->
                    OrderCard(order = order)
                }
            }

        }
    }
}
// Карточка заказа
@Composable
fun OrderCard(order: Order) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Заказ №: ${order.orderNumber}", fontWeight = FontWeight.Bold)
            Text(text = "Сумма: ${order.amount} ₽")
        }
    }
}

// Карточка фотографии
@Composable
fun PhotoCard(photo: Photo) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .padding(4.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Image(
                painter = painterResource(id = photo.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = photo.caption, modifier = Modifier.padding(8.dp))
        }
    }
}

// Пример использования
@Composable
fun ProfileScreenPreview() {
    val orders = listOf(
        Order("12345", 1500.0),
        Order("67890", 2200.0),
        Order("13579", 1000.0),
        Order("24680", 3000.0)
    )
    val photos = listOf(
        Photo(R.drawable.photo_profile1, "Пейзаж 1"),
        Photo(R.drawable.photo_profile1, "Пейзаж 2"),
        Photo(R.drawable.photo_profile1, "Город"),
        Photo(R.drawable.photo_profile1, "Море")
    )
    MaterialTheme {
        AdminProfileScreen(
            onPopBack = { println("Назад") },
            onLogOut = { println("Выход") },
            navController = rememberNavController(),
        )
    }
}