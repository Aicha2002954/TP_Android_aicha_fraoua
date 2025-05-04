package com.example.myapplication.navigator

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
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
        Product("1", "Louboutin So Kate", "750$", "Escarpins iconiques à talon aiguille, élégance ultime."),
        Product("2", "Jimmy Choo Romy", "695$", "Classiques et scintillants, parfaits pour les soirées."),
        Product("3", "Manolo Blahnik Hangisi", "965$", "Avec boucle brillante, symbole du luxe moderne."),
        Product("4", "Christian Dior J’Adior", "890$", "Design raffiné avec ruban signature ‘J’Adior’."),
        Product("5", "Valentino Rockstud", "850$", "Talons avec clous dorés, mélange d’élégance et de rock."),
        Product("6", "Saint Laurent Opyum", "995$", "Reconnaissables par le talon en forme de logo YSL."),
        Product("7", "Gianvito Rossi Plexi", "795$", "Design transparent pour un style aérien et moderne."),
        Product("8", "Prada Slingback", "820$", "Élégance vintage avec un confort exceptionnel.")

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
        Text(text = "🛍️ Application de vente de chaussures", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Rechercher un produit") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(filteredProducts) { product ->
                Button(
                    onClick = { onNavigateToDetails(product.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(text = product.name)
                }
            }
        }
    }
}

@Composable
fun DetailsScreen(productId: String) {
    val products = listOf(
        Product("1", "Louboutin So Kate", "750$", "Escarpins iconiques à talon aiguille, élégance ultime."),
        Product("2", "Jimmy Choo Romy", "695$", "Classiques et scintillants, parfaits pour les soirées."),
        Product("3", "Manolo Blahnik Hangisi", "965$", "Avec boucle brillante, symbole du luxe moderne."),
        Product("4", "Christian Dior J’Adior", "890$", "Design raffiné avec ruban signature ‘J’Adior’."),
        Product("5", "Valentino Rockstud", "850$", "Talons avec clous dorés, mélange d’élégance et de rock."),
        Product("6", "Saint Laurent Opyum", "995$", "Reconnaissables par le talon en forme de logo YSL."),
        Product("7", "Gianvito Rossi Plexi", "795$", "Design transparent pour un style aérien et moderne."),
        Product("8", "Prada Slingback", "820$", "Élégance vintage avec un confort exceptionnel.")

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
            Text(text = "Nom : ${it.name}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Description : ${it.description}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Le prix : ${it.price}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Identifiant : ${it.id}")
        } ?: Text(" Produit non trouvé.")
    }
}
