package com.example.myapplication.ui.theme.auth

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val context = LocalContext.current

    var nom by remember { mutableStateOf("") }
    var prenom by remember { mutableStateOf("") }
    var adresse by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("Client") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    fun validateEmail(email: String) =
        email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun validatePassword(password: String) =
        password.length >= 6

    fun validateNom(nom: String) = nom.isNotBlank()

    fun validatePrenom(prenom: String) = prenom.isNotBlank()

    fun validateAdresse(adresse: String) = adresse.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Inscription", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = nom,
            onValueChange = {
                nom = it
                errorMessage = null
            },
            label = { Text("Nom") },
            isError = nom.isNotBlank() && !validateNom(nom),
            modifier = Modifier.fillMaxWidth()
        )
        if (nom.isNotBlank() && !validateNom(nom)) {
            Text(
                text = "Le nom est obligatoire",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = prenom,
            onValueChange = {
                prenom = it
                errorMessage = null
            },
            label = { Text("Prénom") },
            isError = prenom.isNotBlank() && !validatePrenom(prenom),
            modifier = Modifier.fillMaxWidth()
        )
        if (prenom.isNotBlank() && !validatePrenom(prenom)) {
            Text(
                text = "Le prénom est obligatoire",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = adresse,
            onValueChange = {
                adresse = it
                errorMessage = null
            },
            label = { Text("Adresse") },
            isError = adresse.isNotBlank() && !validateAdresse(adresse),
            modifier = Modifier.fillMaxWidth()
        )
        if (adresse.isNotBlank() && !validateAdresse(adresse)) {
            Text(
                text = "L'adresse est obligatoire",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))


        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = null
            },
            label = { Text("Email") },
            isError = email.isNotBlank() && !validateEmail(email),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        if (email.isNotBlank() && !validateEmail(email)) {
            Text(
                text = "Email non valide",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = null
            },
            label = { Text("Mot de passe") },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image =
                    if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = image,
                        contentDescription = if (passwordVisible) "Masquer le mot de passe" else "Afficher le mot de passe"
                    )
                }
            },
            isError = password.isNotBlank() && !validatePassword(password),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        if (password.isNotBlank() && !validatePassword(password)) {
            Text(
                text = "Le mot de passe doit contenir au moins 6 caractères",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        var expanded by remember { mutableStateOf(false) }
        val roles = listOf("Client", "Admin")

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = role,
                onValueChange = {},
                label = { Text("Rôle") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Choisir rôle"
                        )
                    }
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                roles.forEach { r ->
                    DropdownMenuItem(
                        text = { Text(r) },
                        onClick = {
                            role = r
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        if (errorMessage != null) {
            Text(
                text = errorMessage ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = {
                if (!validateEmail(email)) {
                    errorMessage = "Veuillez saisir un email valide"
                    return@Button
                }
                if (!validatePassword(password)) {
                    errorMessage = "Mot de passe trop court"
                    return@Button
                }

                isLoading = true
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        isLoading = false
                        if (task.isSuccessful) {
                            // ✅ عرض رسالة نجاح
                            Toast.makeText(
                                context,
                                "Inscription réussie ! Veuillez vous connecter.",
                                Toast.LENGTH_LONG
                            ).show()

                            // ✅ تحويل المستخدم لصفحة تسجيل الدخول
                            onRegisterSuccess()
                        } else {
                            errorMessage = "Erreur: ${task.exception?.localizedMessage}"
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("S'inscrire")
            }
        }
    }}