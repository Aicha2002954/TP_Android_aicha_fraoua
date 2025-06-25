package com.example.myapplication.ui.theme.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.Entities.User
import com.example.myapplication.data.repository.UserRepository

import androidx.compose.material.icons.filled.*
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.product.components.AppFooter
import com.example.myapplication.ui.theme.product.components.AppHeader
import androidx.compose.ui.text.font.FontWeight


@Composable
fun RegisterScreen(
    navController: NavController,
    cartItemCount: Int,
    onRegisterSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("client") }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    val isEmailValid = email.contains("@") && email.contains(".")
    val isPasswordValid = password.length >= 6
    val isNameValid = name.trim().isNotEmpty()
    val isAddressValid = address.trim().isNotEmpty()
    val isPhoneValid = phone.matches(Regex("^(\\+?\\d{8,15})$"))

    val allValid = isEmailValid && isPasswordValid && isNameValid && isAddressValid && isPhoneValid

    Column(modifier = Modifier.fillMaxSize()) {
        AppHeader()

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Text(
                    "Créer un compte",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1D0057),
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            item {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nom complet") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = !isNameValid && name.isNotEmpty(),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            item {
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Adresse") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = !isAddressValid && address.isNotEmpty(),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            item {
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Téléphone") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = !isPhoneValid && phone.isNotEmpty(),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            item {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = !isEmailValid && email.isNotEmpty(),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            item {
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Mot de passe") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = !isPasswordValid && password.isNotEmpty(),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            item {
                Text(
                    "Rôle",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("client", "admin", "agent").forEach { roleOption ->
                        Button(
                            onClick = { role = roleOption },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (role == roleOption) Color(0xFF6A4C93) else Color.LightGray,
                                contentColor = if (role == roleOption) Color.White else Color.Black
                            ),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(roleOption.replaceFirstChar { it.uppercase() })
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        if (allValid) {
                            val newUser = User(email, password, name, address, phone, role)
                            val success = UserRepository.registerUser(newUser)
                            if (success) {
                                errorMessage = null
                                onRegisterSuccess()
                            } else {
                                errorMessage = "Cet email est déjà enregistré."
                            }
                        } else {
                            errorMessage = "Merci de remplir correctement tous les champs."
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A4C93)),
                    enabled = allValid
                ) {
                    Text("S'inscrire", color = Color.White, fontSize = 18.sp)
                }
            }

            item {
                errorMessage?.let {
                    Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        AppFooter(navController = navController, cartItemCount = cartItemCount)
    }
}
