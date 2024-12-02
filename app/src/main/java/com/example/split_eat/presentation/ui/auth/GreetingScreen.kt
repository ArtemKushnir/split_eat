package com.example.split_eat.presentation.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.split_eat.R
import com.example.split_eat.presentation.ui.theme.Tomato
import com.example.split_eat.presentation.viewmodel.AuthViewModel


@Composable
fun GreetingScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        LogoImage()
        GreetingText()
        RegistrationButton(navController)
        LoginText(navController)

    }
}

@Composable
fun LogoImage(){
    Image(
        painter = painterResource(id = R.drawable.app_logo),
        contentDescription = "Logo",
        modifier = Modifier
            .size(450.dp)
            .padding(bottom = 32.dp, top = 22.dp)
    )
}

@Composable
fun GreetingText(){
    val annotatedText = buildAnnotatedString {
        append("Добро пожаловать в ")
        withStyle(style = SpanStyle(color = Tomato)) {
            append("Split-Eat")
        }
    }
    Text(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        text = annotatedText,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(bottom = 150.dp)
    )
}

@Composable
fun RegistrationButton(navController: NavController) {
    CustomButton(
        text = "Зарегистрироваться",
        onClick = { navController.navigate("register") },
        modifier = Modifier
            .width(300.dp)
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Black,
            containerColor = Tomato
        )
    )
}

@Composable
fun LoginText(navController: NavController){
    Row {
        Text(
            text = "Уже есть аккаунт? ",
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top=5.dp)
        )
        Text(
            text = "Войти",
            color = Tomato,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .clickable(onClick = { navController.navigate("login") })
                .padding(top=5.dp)
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//fun GreetingScreenPreview() {
//    GreetingScreen()
//}