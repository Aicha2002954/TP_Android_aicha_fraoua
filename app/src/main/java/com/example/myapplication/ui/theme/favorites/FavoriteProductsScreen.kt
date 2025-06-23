package com.example.myapplication.ui.theme.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.Entities.Product
import com.example.myapplication.ui.theme.product.components.ProductItemComponent
import com.example.myapplication.ui.theme.product.ProductViewModel
import com.example.myapplication.ui.theme.product.components.FavoriteProductItemComponent

@Composable
fun FavoriteProductsScreen(
    viewModel: ProductViewModel,
    onProductClick: (Product) -> Unit
) {
    val favoriteProducts = viewModel.favorites

    if (favoriteProducts.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Aucun produit en favori", color = MaterialTheme.colorScheme.onSurface)
        }
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(favoriteProducts) { product ->
                FavoriteProductItemComponent(
                    product = product,
                    viewModel = viewModel,
                    onClick = { onProductClick(product) },
                    onAddToCart = { viewModel.addToCart(product) }
                )
            }
        }
    }
}
