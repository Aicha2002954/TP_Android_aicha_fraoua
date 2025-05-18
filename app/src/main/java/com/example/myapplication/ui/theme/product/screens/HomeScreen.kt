
package com.example.myapplication.ui.product.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.product.ProductIntent
import com.example.myapplication.ui.theme.product.ProductViewModel
import com.example.myapplication.ui.theme.product.components.AppHeader
import com.example.myapplication.ui.theme.product.components.EmptyProductsMessage
import com.example.myapplication.ui.theme.product.components.ProductsGrid
import com.example.myapplication.ui.theme.product.components.SearchBar

@Composable
fun HomeScreen(
    viewModel: ProductViewModel,
    onProductClick: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val viewState = viewModel.viewState
    val products = viewState.products

    val filteredProducts = products.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        viewModel.onIntent(ProductIntent.LoadProducts)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F4FF))
    ) {
        AppHeader()

        SearchBar(
            searchQuery = searchQuery,
            onSearchQueryChanged = { searchQuery = it },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (filteredProducts.isEmpty()) {
            EmptyProductsMessage()
        } else {
            ProductsGrid(
                products = filteredProducts,
                onNavigateToDetails = onProductClick
            )
        }
    }
}
