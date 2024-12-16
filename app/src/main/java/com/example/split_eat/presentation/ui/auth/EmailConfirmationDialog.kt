package com.example.split_eat.presentation.ui.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.split_eat.presentation.ui.theme.Tomato
import com.example.split_eat.presentation.viewmodel.AuthViewModel

@Composable
fun EmailConfirmationDialog(
    email: String, onNavigate: () -> Unit, onPopBack: () -> Unit
) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val context = LocalContext.current

    LaunchedEffect(authViewModel.isLoggedIn.value) {
        if (authViewModel.isLoggedIn.value) {
            onNavigate()
        }
    }

    LaunchedEffect(Unit) {
        authViewModel.messageEvent.collect {
                message -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
    var code by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = "Подтверждение почты") },
        text = {
            Column {
                Text(text = "Мы отправили код подтверждения на $email. Введите его ниже:")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    label = { Text("Код подтверждения") },
                    placeholder = { Text("Введите код") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { authViewModel.confirmEmail(email, code) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Tomato,
                    contentColor = Color.Black
                )
            ) {
                Text("ОК")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onPopBack() },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Black,
                    containerColor = Color.Gray
                )
            ) {
                Text("Отмена")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ConfirmEmailPreview() {
    EmailConfirmationDialog("email", {println("main")}, { println("pop_back") })
}