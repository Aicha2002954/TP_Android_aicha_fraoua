package com.example.myapplication.ui.product.screens
import androidx.compose.foundation.layout.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.product.ProductViewModel
import com.example.myapplication.ui.theme.product.components.AppHeader
import com.example.myapplication.ui.theme.product.components.BackButton
import com.example.myapplication.ui.theme.product.components.ProductDetailsCard

@Composable
fun DetailsScreen(productId: String, viewModel: ProductViewModel, onBack: () -> Unit) {
    val product = viewModel.getProductById(productId)

    Column(modifier = Modifier.fillMaxSize()) {
        AppHeader()
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProductDetailsCard(product = product)

            Spacer(modifier = Modifier.height(16.dp))

            BackButton(onBack = onBack)
        }
    }


}
