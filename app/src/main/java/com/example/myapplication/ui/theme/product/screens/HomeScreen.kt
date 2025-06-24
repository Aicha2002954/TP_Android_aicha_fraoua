package com.example.myapplication.ui.product.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            Category.Fournitures -> categoryNormalized == "fournitures"
            Category.Pyjamas -> categoryNormalized == "pyjamas"
        }

        matchesSearch && matchesCategory
    }

    val scrollState = rememberScrollState()

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
                .horizontalScroll(scrollState)
                .padding(vertical = 8.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Category.values().forEach { category ->
                val isSelected = category == selectedCategory

                val backgroundColor = if (isSelected) Color(0xFF7E57C2) else Color(0xFFEDE7F6)
                val contentColor = if (isSelected) Color.White else Color(0xFF5E35B1)

                val imageRes = when (category) {
                    Category.All -> R.drawable.image1
                    Category.Garcon -> R.drawable.garcon
                    Category.Fille -> R.drawable.logofi
                    Category.Chaussures -> R.drawable.chasseur2
                    Category.Jouets -> R.drawable.joutes2
                    Category.Fournitures -> R.drawable.fourinteurlogo
                    Category.Pyjamas -> R.drawable.payjma3
                }

                Column(
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .background(backgroundColor)
                        .clickable { selectedCategory = category }
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                ) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = category.name,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = when (category) {
                            Category.All -> "Vêtements bébés"
                            Category.Garcon -> "Garçon"
                            Category.Fille -> "Fille"
                            Category.Chaussures -> "Chaussures"
                            Category.Jouets -> "Jouets"
                            Category.Fournitures -> "Fournitures"
                            Category.Pyjamas -> "Pyjamas"
                        },
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 14.sp,
                        color = contentColor
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
