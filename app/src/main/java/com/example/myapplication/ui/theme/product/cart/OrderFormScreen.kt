package com.example.myapplication.ui.theme.product.cart
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.myapplication.R

import com.example.myapplication.ui.theme.product.ProductViewModel
import com.example.myapplication.ui.theme.product.components.AppFooter
import com.example.myapplication.ui.theme.product.components.AppHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderFormScreen(
    navController: NavController,
    viewModel: ProductViewModel,
    onLanguageSelected: (String) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("") }
    var showCardForm by remember { mutableStateOf(false) }
    val cartItemCount = viewModel.cartItemCount
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvc by remember { mutableStateOf("") }
    val creditCardLabel = stringResource(id = R.string.credit_card)

    var showErrors by remember { mutableStateOf(false) }

    val paymentOptions = listOf(
        stringResource(id = R.string.credit_card),
        stringResource(id = R.string.paypal),
        stringResource(id = R.string.cash_on_delivery)
    )

    Scaffold(
        topBar = {
            AppHeader(onLanguageSelected = onLanguageSelected)
        },
        bottomBar = {
            AppFooter(navController = navController, cartItemCount = cartItemCount)
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
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
                    label = { Text(stringResource(id = R.string.full_name)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showErrors && name.isBlank()
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(stringResource(id = R.string.email)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showErrors && email.isBlank()
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text(stringResource(id = R.string.address)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showErrors && address.isBlank()
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text(stringResource(id = R.string.phone)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showErrors && phone.isBlank()
                )

                Text(
                    text = stringResource(id = R.string.payment_method),
                    style = MaterialTheme.typography.titleSmall
                )

                paymentOptions.forEach { option ->
                    Row(
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = paymentMethod == option,
                            onCheckedChange = {
                                paymentMethod = if (it) option else ""
                            }
                        )
                        Text(option)
                    }
                }

                if (showErrors && paymentMethod.isBlank()) {
                    Text(
                        text = stringResource(id = R.string.choose_payment_error),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Button(
                    onClick = {
                        if (name.isBlank() || email.isBlank() || address.isBlank() || phone.isBlank() || paymentMethod.isBlank()) {
                            showErrors = true
                        } else {
                            showErrors = false
                            if (paymentMethod == creditCardLabel) {
                                showCardForm = true
                            } else {
                                viewModel.saveOrderInfo(name, email, address, phone, paymentMethod)
                                viewModel.setOrderItems(viewModel.cart)
                                navController.navigate("confirmation")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(stringResource(id = R.string.continuer), color = MaterialTheme.colorScheme.onPrimary)
                }
            } else {
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { cardNumber = it },
                    label = { Text(stringResource(id = R.string.card_number)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showErrors && cardNumber.isBlank()
                )

                OutlinedTextField(
                    value = expiryDate,
                    onValueChange = { expiryDate = it },
                    label = { Text(stringResource(id = R.string.expiry_date)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showErrors && expiryDate.isBlank()
                )

                OutlinedTextField(
                    value = cvc,
                    onValueChange = { cvc = it },
                    label = { Text(stringResource(id = R.string.cvc)) },
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
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(id = R.string.back))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(stringResource(id = R.string.back), color = MaterialTheme.colorScheme.onTertiary)
                    }

                    Button(
                        onClick = {
                            if (cardNumber.isNotBlank() && expiryDate.isNotBlank() && cvc.isNotBlank()) {
                                showErrors = false
                                viewModel.saveOrderInfo(name, email, address, phone, paymentMethod)
                                viewModel.setOrderItems(viewModel.cart)
                                navController.navigate("confirmation")
                            } else {
                                showErrors = true
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text(stringResource(id = R.string.confirm_payment), color = MaterialTheme.colorScheme.onSecondary)
                    }
                }
            }
        }
    }
}
