package com.example.split_eat.presentation.ui.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.split_eat.presentation.ui.theme.Tomato
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.split_eat.presentation.viewmodel.AuthViewModel
import androidx.compose.ui.platform.LocalContext


@Composable
fun LoginScreen(navController: NavController) {
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
            LoginCard(navController)
        }
    }
}



@Composable
fun LogoText(){
    Text(
        text = "Split-Eat",
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
        color = Tomato,
        modifier = Modifier
            .padding(top = 10.dp)
    )
}

@Composable
fun LoginCard(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(390.dp)
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
            Text(text = "Пароль", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            PasswordTextField(password = password, onPasswordChange = { password = it })
            LoginButton(navController, email, password)
        }

    }
}

@Composable
fun EmailTextField(email: String, onEmailChange: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        shape = RoundedCornerShape(6.dp),
        placeholder = { Text("email") },
        singleLine = true,
        modifier = Modifier.padding(bottom = 5.dp)
    )
}


@Composable
fun PasswordTextField(password: String, onPasswordChange: (String) -> Unit){
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        shape = RoundedCornerShape(6.dp),
        placeholder = {Text("password")},
        visualTransformation = PasswordVisualTransformation(),
        singleLine = true)
}


@Composable
fun LoginButton(navController: NavController, email: String, password: String){
    val authViewModel: AuthViewModel = hiltViewModel()
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        authViewModel.navigationEvent.collect { destination ->
            navController.navigate(destination)
        }
    }

    LaunchedEffect(Unit) {
        authViewModel.messageEvent.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    CustomButton(
        text = "Войти",
        onClick = { authViewModel.login(email, password) },
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
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
    contentPadding: PaddingValues = PaddingValues(16.dp)
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = colors,
        shape = shape,
        contentPadding = contentPadding
    ) {
        Text(text = text)
    }
}


//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen()
//}