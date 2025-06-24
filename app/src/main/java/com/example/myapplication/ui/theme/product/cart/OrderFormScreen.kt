package com.example.myapplication.ui.theme.product.cart

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.product.ProductViewModel
import com.example.myapplication.ui.theme.product.components.AppFooter
import com.example.myapplication.ui.theme.product.components.AppHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderFormScreen(
    navController: NavController,
    viewModel: ProductViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }

    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var showCardForm by remember { mutableStateOf(false) }

    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvc by remember { mutableStateOf("") }

    var showErrors by remember { mutableStateOf(false) }

    val paymentOptions = listOf("Carte bancaire", "PayPal", "Paiement à la livraison")

    Scaffold(
        topBar = { AppHeader() },
        bottomBar = { AppFooter(navController) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            if (!showCardForm) {

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nom complet") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showErrors && name.isBlank()
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Adresse de livraison") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showErrors && address.isBlank()
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Téléphone") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showErrors && phone.isBlank()
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = paymentMethod,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Mode de paiement") },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        isError = showErrors && paymentMethod.isBlank()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        paymentOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    paymentMethod = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        if (name.isBlank() || address.isBlank() || phone.isBlank() || paymentMethod.isBlank()) {
                            showErrors = true
                        } else {
                            showErrors = false
                            if (paymentMethod == "Carte bancaire") {
                                showCardForm = true
                            } else {
                                viewModel.saveOrderInfo(name, address, phone, paymentMethod)
                                navController.navigate("confirmation")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Continuer", color = MaterialTheme.colorScheme.onPrimary)
                }
            } else {

                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { cardNumber = it },
                    label = { Text("Numéro de carte") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showErrors && cardNumber.isBlank()
                )

                OutlinedTextField(
                    value = expiryDate,
                    onValueChange = { expiryDate = it },
                    label = { Text("Date d’expiration (MM/AA)") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showErrors && expiryDate.isBlank()
                )

                OutlinedTextField(
                    value = cvc,
                    onValueChange = { cvc = it },
                    label = { Text("CVC") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showErrors && cvc.isBlank()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            showCardForm = false
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Retour", color = MaterialTheme.colorScheme.onTertiary)
                    }

                    Button(
                        onClick = {
                            if (cardNumber.isNotBlank() && expiryDate.isNotBlank() && cvc.isNotBlank()) {
                                showErrors = false
                                viewModel.saveOrderInfo(name, address, phone, paymentMethod)
                                navController.navigate("confirmation")
                            } else {
                                showErrors = true
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text("Valider le paiement", color = MaterialTheme.colorScheme.onSecondary)
                    }
                }
            }
        }
    }
}
