package com.example.myapplication.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.Product
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

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
        Product("1", "Louboutin So Kate", "750$", "Escarpins iconiques à talon aiguille, élégance ultime.",R.drawable.image3),
        Product("2", "Jimmy Choo Romy", "695$", "Classiques et scintillants, parfaits pour les soirées.",R.drawable.image4),
        Product("3", "Manolo Blahnik Hangisi", "965$", "Avec boucle brillante, symbole du luxe moderne.",R.drawable.image5),
        Product("4", "Christian Dior J’Adior", "890$", "Design raffiné avec ruban signature ‘J’Adior’.",R.drawable.image1),
        Product("5", "Valentino Rockstud", "850$", "Talons avec clous dorés, mélange d’élégance et de rock.",R.drawable.image7),
        Product("6", "Saint Laurent Opyum", "995$", "Reconnaissables par le talon en forme de logo YSL.",R.drawable.image8),
        Product("7", "Gianvito Rossi Plexi", "795$", "Design transparent pour un style aérien et moderne.",R.drawable.image9),
        Product("12", "Prada Slingback", "820$", "Élégance vintage avec un confort exceptionnel.",R.drawable.image1)
    )
    val filteredProducts = products.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }.take(8)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "🛍 Application de vente de chaussures", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Rechercher un produit") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        filteredProducts.forEach { product ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = product.imageResId),
                        contentDescription = product.name,
                        modifier = Modifier.size(80.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = product.price, style = MaterialTheme.typography.bodyMedium)
                    }

                    Button(onClick = { onNavigateToDetails(product.id) }) {
                        Text("Détails")
                    }
                }
            }
        }
    }
}

@Composable
fun DetailsScreen(productId: String) {
    val products = listOf(
        Product("1", "Louboutin So Kate", "750$", "Escarpins iconiques à talon aiguille, élégance ultime.",R.drawable.image3),
        Product("2", "Jimmy Choo Romy", "695$", "Classiques et scintillants, parfaits pour les soirées.",R.drawable.image4),
        Product("3", "Manolo Blahnik Hangisi", "965$", "Avec boucle brillante, symbole du luxe moderne.",R.drawable.image5),
        Product("4", "Christian Dior J’Adior", "890$", "Design raffiné avec ruban signature ‘J’Adior’.",R.drawable.image1),
        Product("5", "Valentino Rockstud", "850$", "Talons avec clous dorés, mélange d’élégance et de rock.",R.drawable.image7),
        Product("6", "Saint Laurent Opyum", "995$", "Reconnaissables par le talon en forme de logo YSL.",R.drawable.image8),
        Product("7", "Gianvito Rossi Plexi", "795$", "Design transparent pour un style aérien et moderne.",R.drawable.image9),
        Product("12", "Prada Slingback", "820$", "Élégance vintage avec un confort exceptionnel.",R.drawable.image1)
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
        } ?: Text(" Produit non trouvé.")
    }
}
