package com.example.split_eat.presentation.ui.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.split_eat.presentation.ui.theme.Tomato
import com.example.split_eat.presentation.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LogoText()
            RegisterCard(navController)
        }
    }
}

@Composable
fun RegisterCard(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(560.dp)
            .padding(top = 100.dp)
            .border(2.dp, Color.Black, shape = RoundedCornerShape(15.dp)),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "ST почта", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            EmailTextField(email = email, onEmailChange = { email = it })
            Text(text = "Никнейм", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            UsernameTextField(username = username, onUsernameChange = { username = it })
            Text(text = "Пароль", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            PasswordTextField(password = password, onPasswordChange = { password = it })
            Text(text = "Подтвердите пароль", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 5.dp))
            ConfirmPasswordTextField(confirmPassword = confirmPassword, onConfirmPasswordChange = {confirmPassword = it})
            RegisterButton(navController, email, username, password, confirmPassword)
        }

    }
}


@Composable
fun RegisterButton(navController: NavController, email: String, username: String, password:String, confirmPassword: String){
    val authViewModel: AuthViewModel = hiltViewModel()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        authViewModel.navigationEvent.collect { destination ->
            navController.navigate(destination)
        }
    }

    LaunchedEffect(Unit) {
        authViewModel.messageEvent.collect {
            message -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    CustomButton(
        text = "Зарегистрироваться",
        onClick = {authViewModel.register(email, username, password, confirmPassword)},
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Black,
            containerColor = Tomato
        ),
        modifier = Modifier
            .padding(top = 30.dp)
            .fillMaxSize()
    )
}
@Composable
fun UsernameTextField(username: String, onUsernameChange: (String) -> Unit){
    OutlinedTextField(
        value = username,
        onValueChange = onUsernameChange,
        shape = RoundedCornerShape(6.dp),
        placeholder = {Text("username")},
        singleLine = true,
        modifier = Modifier.padding(bottom = 5.dp))
}

@Composable
fun ConfirmPasswordTextField(confirmPassword: String, onConfirmPasswordChange: (String) -> Unit){
    OutlinedTextField(
        value = confirmPassword,
        onValueChange = onConfirmPasswordChange,
        shape = RoundedCornerShape(6.dp),
        placeholder = {Text("password")},
        visualTransformation = PasswordVisualTransformation(),
        singleLine = true,
        modifier = Modifier.padding(top = 8.dp))
}

//@Preview(showBackground = true)
//@Composable
//fun RegisterScreenPreview() {
//    RegisterScreen()
//}