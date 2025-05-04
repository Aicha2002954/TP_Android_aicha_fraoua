package com.example.myapplication.navigator

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.R
import com.example.myapplication.model.Product

object Routes {
    const val Home = "home"
    const val ProductDetails = "productDetails"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Home) {
        composable(Routes.Home) {
            HomeScreen(onNavigateToDetails = { productId ->
                navController.navigate("${Routes.ProductDetails}/$productId")
            })
        }

        composable(
            "${Routes.ProductDetails}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            DetailsScreen(productId = productId)
        }
    }
}

@Composable
fun HomeScreen(onNavigateToDetails: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    val products = listOf(
        Product("1", "Robe de soirée", "99€", "Robe élégante en satin, parfaite pour les grandes occasions.", R.drawable.image7),
        Product("2", "Jean slim", "59€", "Jean stylé, coupe slim et confortable pour un look moderne.", R.drawable.image4),
        Product("3", "T-shirt floral", "29€", "T-shirt léger et frais avec motifs floraux, idéal pour l'été.", R.drawable.image8),
        Product("4", "Robe en laine", "120€", "Robe d'hiver chaud et chic, un incontournable de la saison.", R.drawable.image3),
        Product("5", "Jupe en cuir", "79€", "Jupe tendance en cuir, pour un look audacieux et stylé.", R.drawable.image1),
        Product("6", "Baskets blanches", "65€", "Baskets confortables et élégantes, adaptées à toutes les occasions.", R.drawable.image9),
        Product("7", "Pull en cachemire", "150€", "Pull doux et chaud, en cachemire pour une douceur extrême.", R.drawable.image10),
        Product("8", "Blouse à volants", "49€", "Blouse légère avec détails à volants, pour un look féminin et élégant.", R.drawable.image2),
        Product("9", "Veste en jean", "89€", "Veste décontractée en jean, parfaite pour toutes les saisons.", R.drawable.image6),

        )

    val filteredProducts = products.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Petit Papillon 🦋", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("\uD83E\uDD8BRechercher un produit") },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(filteredProducts) { product ->
                ProductCard(
                    product = product,
                    onNavigateToDetails = onNavigateToDetails
                )
            }
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    onNavigateToDetails: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(1.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F8),
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = product.imageResId),
                contentDescription = product.name,
                modifier = Modifier
                    .size(220.dp)
                    .padding(bottom =17.dp)
            )

            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Text(
                text = product.price,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Button(
                onClick = { onNavigateToDetails(product.id) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text("Détails", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun DetailsScreen(productId: String) {
    val products = listOf(
        Product("1", "Robe de soirée", "99€", "Robe élégante en satin, parfaite pour les grandes occasions.", R.drawable.image7),
        Product("2", "Jean slim", "59€", "Jean stylé, coupe slim et confortable pour un look moderne.", R.drawable.image4),
        Product("3", "T-shirt floral", "29€", "T-shirt léger et frais avec motifs floraux, idéal pour l'été.", R.drawable.image8),
        Product("4", "Robe en laine", "120€", "Robe d'hiver chaud et chic, un incontournable de la saison.", R.drawable.image3),
        Product("5", "Jupe en cuir", "79€", "Jupe tendance en cuir, pour un look audacieux et stylé.", R.drawable.image1),
        Product("6", "Baskets blanches", "65€", "Baskets confortables et élégantes, adaptées à toutes les occasions.", R.drawable.image9),
        Product("7", "Pull en cachemire", "150€", "Pull doux et chaud, en cachemire pour une douceur extrême.", R.drawable.image10),
        Product("8", "Blouse à volants", "49€", "Blouse légère avec détails à volants, pour un look féminin et élégant.", R.drawable.image2),
        Product("9", "Veste en jean", "89€", "Veste décontractée en jean, parfaite pour toutes les saisons.", R.drawable.image6),

    )
    val product = products.find { it.id == productId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "🧾 Détails du produit", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        product?.let {
            Image(
                painter = painterResource(id = it.imageResId),
                contentDescription = it.name,
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Nom : ${it.name}", fontSize = 20.sp)
            Text(text = "Prix : ${it.price}", fontSize = 18.sp)
            Text(text = "Description : ${it.description}", fontSize = 16.sp)
            Text(text = "ID : ${it.id}", fontSize = 14.sp)
        } ?: Text("Produit non trouvé.")
    }
}