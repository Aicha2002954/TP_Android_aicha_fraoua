package com.example.myapplication.ui.product.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.theme.product.ProductIntent
import com.example.myapplication.ui.theme.product.ProductViewModel
import com.example.myapplication.ui.theme.product.components.AppFooter
import com.example.myapplication.ui.theme.product.components.AppHeader
import com.example.myapplication.ui.theme.product.components.EmptyProductsMessage
import com.example.myapplication.ui.theme.product.components.ProductsGrid
import com.example.myapplication.ui.theme.product.components.SearchBar
import java.text.Normalizer
import androidx.compose.runtime.remember as remember1
enum class Category {
    All,
    Garcon,
    Fille,
    Chaussures,
    Jouets,
    Fournitures,
    Pyjamas
}

fun normalizeCategory(cat: String?): String {
    return Normalizer.normalize(cat ?: "", Normalizer.Form.NFD)
        .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
        .lowercase()
        .trim()
}

@Composable
fun HomeScreen(
    viewModel: ProductViewModel,
    onProductClick: (String) -> Unit,
    navController: NavController
) {
    var searchQuery by remember1 { mutableStateOf("") }
    var selectedCategory by remember1 { mutableStateOf(Category.All) }

    val viewState = viewModel.viewState
    val products = viewState.products
    val cartItemCount = viewModel.cartItemCount

    LaunchedEffect(Unit) {
        viewModel.onIntent(ProductIntent.LoadProducts)
    }

    val filteredProducts = products.filter { product ->
        val matchesSearch = product.name.contains(searchQuery, ignoreCase = true) ||
                product.description.contains(searchQuery, ignoreCase = true)

        val categoryNormalized = normalizeCategory(product.category)

        val matchesCategory = when (selectedCategory) {
            Category.All -> true
            Category.Garcon -> categoryNormalized == "garcon"
            Category.Fille -> categoryNormalized == "fille"
            Category.Chaussures -> categoryNormalized == "chaussures"
            Category.Jouets -> categoryNormalized == "jouets"
            Category.Fournitures -> categoryNormalized == "fourniteurs"
            Category.Pyjamas -> categoryNormalized == "payjames"
        }

        matchesSearch && matchesCategory
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Category.values().forEach { category ->
                val imageRes = when (category) {
                    Category.All -> R.drawable.image1
                    Category.Garcon -> R.drawable.garcon
                    Category.Fille -> R.drawable.ffil
                    Category.Chaussures -> R.drawable.image1
                    Category.Jouets -> R.drawable.image2
                    Category.Fournitures -> R.drawable.image2
                    Category.Pyjamas -> R.drawable.image5
                }

                Column(
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { selectedCategory = category }
                        .background(
                            if (selectedCategory == category) Color(0xFFE1BEE7) else Color.Transparent,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = category.name,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                    )

                    Text(
                        text = when (category) {
                            Category.All -> "Vetments pour bebes"
                            Category.Garcon -> "Garçon"
                            Category.Fille -> "Fille"
                            Category.Chaussures -> "Chaussures"
                            Category.Jouets -> "Jouets"
                            Category.Fournitures -> "Fournitures"
                            Category.Pyjamas -> "Pyjamas"
                        },
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.weight(1f)) {
            if (filteredProducts.isEmpty()) {
                EmptyProductsMessage()
            } else {
                ProductsGrid(
                    products = filteredProducts,
                    viewModel = viewModel,
                    onNavigateToDetails = onProductClick
                )
            }
        }

        AppFooter(navController = navController, cartItemCount = cartItemCount)
    }
}
