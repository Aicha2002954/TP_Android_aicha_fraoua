package com.example.myapplication.ui.product.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.product.ProductViewModel
import com.example.myapplication.ui.theme.product.components.AppFooter
import com.example.myapplication.ui.theme.product.components.AppHeader
import com.example.myapplication.ui.theme.product.components.ProductDetailsCard

@Composable
fun DetailsScreen(
    productId: String,
    viewModel: ProductViewModel,
    onBack: () -> Unit,
    navController: NavController
) {
    val product = viewModel.getProductById(productId)
    val offer = viewModel.getOfferForProduct(productId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F4FF))
    ) {
        AppHeader()
        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProductDetailsCard(
                product = product,
                onBack = onBack,
                offerPercent = offer?.discountPercent,
                onAddToCartWithSize = { product, size ->
                    val originalPrice =
                        product.price.removeSuffix("€").trim().replace(",", ".").toDoubleOrNull()
                            ?: 0.0
                    val priceToUse =
                        offer?.let { (originalPrice * (100 - it.discountPercent) / 100) }
                            ?: originalPrice
                    viewModel.addToCartWithSizeAndPrice(product, size, priceToUse)
                }
                    )
            Spacer(modifier = Modifier.height(4.dp))
        }

        AppFooter(navController = navController, cartItemCount = viewModel.cartItemCount)
    }
}
