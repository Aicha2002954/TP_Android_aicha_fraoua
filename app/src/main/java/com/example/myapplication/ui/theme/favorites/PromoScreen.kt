package com.example.myapplication.ui.theme.favorites


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.data.Entities.Product
import com.example.myapplication.ui.theme.product.ProductViewModel
import com.example.myapplication.ui.theme.product.components.AppFooter
import com.example.myapplication.ui.theme.product.components.AppHeader
import com.example.myapplication.ui.theme.product.components.ProductItemComponent

@Composable
fun PromoScreen(
    viewModel: ProductViewModel,
    navController: NavController,
    onProductClick: (Product) -> Unit,
    onLanguageSelected: (String) -> Unit
) {
    val cartItemCount = viewModel.cartItemCount
    val productsWithOffer = viewModel.viewState.products.filter { product ->
        viewModel.getOfferForProduct(product.id) != null
    }
    Scaffold(
        topBar = {   AppHeader(onLanguageSelected = onLanguageSelected)

        },
        bottomBar = { AppFooter(navController = navController, cartItemCount = cartItemCount) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Découvrez nos offres exceptionnelles ",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color(0xFF6A1B9A),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                if (productsWithOffer.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocalOffer,
                            contentDescription = "Aucune promotion",
                            tint = Color(0xFF9C27B0),
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Aucune promotion en ce moment",
                            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
                        )
                    }
                } else {
LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(productsWithOffer) { product ->
                            ProductItemComponent(
                                product = product,
                                viewModel = viewModel,
                                onClick = { onProductClick(product) }
                            )
                        }
                    }
                }
            }
        }
    }
}
