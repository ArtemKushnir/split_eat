package com.example.split_eat.presentation.ui.auth

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.split_eat.presentation.ui.theme.Tomato

@Composable
fun EmailConfirmationDialog(
    email: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    var code by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = { onDismiss() }, // Закрытие при клике вне окна
        title = {
            Text(text = "Подтверждение почты")
        },
        text = {
            Column {
                Text(
                    text = "Мы отправили код подтверждения на $email. Введите его ниже:",
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = code, // Состояние поля ввода
                    onValueChange = { code = it }, // Обновление состояния
                    label = { Text("Код подтверждения") },
                    placeholder = { Text("Введите код") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
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
                onClick = onDismiss,
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
fun EmailConfirmWindowPreview() {
    EmailConfirmationDialog(email = "admin@example.com", onConfirm = { }) {
    }
}