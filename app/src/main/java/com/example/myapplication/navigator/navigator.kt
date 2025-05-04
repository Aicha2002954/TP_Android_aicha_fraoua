package com.example.myapplication.navigator

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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

    NavHost(
        navController = navController,
        startDestination = Routes.Home
    ) {
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
            DetailsScreen(productId = productId, onBack = { navController.popBackStack() })
        }
    }
}

@Composable
fun HomeScreen(onNavigateToDetails: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    val products = listOf(
        Product("1", "Jean slim", "99€", "Jean stylé, coupe slim et confortable pour un look moderne.", R.drawable.image7),
        Product("2", "Robe de soirée", "129€", "Robe élégante en satin, parfaite pour les grandes occasions.", R.drawable.image4),
        Product("3", "T-shirt floral", "29€", "T-shirt léger et frais avec motifs floraux, idéal pour l'été.", R.drawable.image8),
        Product("4", "Robe en laine", "120€", "Robe d'hiver chaude et chic, un incontournable de la saison.", R.drawable.image3),
        Product("5", "Jupe en cuir", "79€", "Jupe tendance en cuir, pour un look audacieux et stylé.", R.drawable.image1),
        Product("6", "Baskets blanches", "65€", "Baskets confortables et élégantes, adaptées à toutes les occasions.", R.drawable.image9),
        Product("7", "Pull en cachemire", "150€", "Pull doux et chaud, en cachemire pour une douceur extrême.", R.drawable.image10),
        Product("8", "Blouse à volants", "49€", "Blouse légère avec détails à volants, pour un look féminin et élégant.", R.drawable.image2),
        Product("9", "Veste en jean", "89€", "Veste décontractée en jean, parfaite pour toutes les saisons.", R.drawable.image6),
    )

    val filteredProducts = products.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.description.contains(searchQuery, ignoreCase = true)
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

        if (filteredProducts.isEmpty()) {
            EmptyProductsMessage()
        } else {
            ProductsGrid(
                products = filteredProducts,
                onNavigateToDetails = onNavigateToDetails
            )
        }
    }
}

@Composable
fun AppHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFB45AAB),
                        Color(0xFFBD98DE)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Petit Papillon 🦋",
            style = MaterialTheme.typography.headlineLarge.copy(color = Color.White),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .offset(y = (-20).dp)
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
    ) {
        TextField(
            value = searchQuery,
            onValueChange = onSearchQueryChanged,
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Rechercher",
                    tint = Color(0xFF9C27B0)
                )
            },
            label = { Text("Rechercher un produit...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {})
        )
    }
}

@Composable
fun EmptyProductsMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "Aucun produit trouvé 🦋",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
        )
    }
}

@Composable
fun ProductsGrid(
    products: List<Product>,
    onNavigateToDetails: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 16.dp, top = 8.dp)
    ) {
        items(products) { product ->
            ProductCard(
                product = product,
                onNavigateToDetails = onNavigateToDetails
            )
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
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(19.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = product.imageResId),
                contentDescription = product.name,
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFF6A1B9A)),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = product.price,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color(0xFF672037),
                    fontSize = 18.sp
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Button(
                onClick = { onNavigateToDetails(product.id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF9C27B0),
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 4.dp
                )
            ) {
                Text("Voir détails", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun DetailsScreen(productId: String, onBack: () -> Unit) {
    val products = listOf(
        Product("1", "Jean slim", "99€", "Jean stylé, coupe slim et confortable pour un look moderne.", R.drawable.image7),
        Product("2", "Robe de soirée", "129€", "Robe élégante en satin, parfaite pour les grandes occasions.", R.drawable.image4),
        Product("3", "T-shirt floral", "29€", "T-shirt léger et frais avec motifs floraux, idéal pour l'été.", R.drawable.image8),
        Product("4", "Robe en laine", "120€", "Robe d'hiver chaude et chic, un incontournable de la saison.", R.drawable.image3),
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
            .background(Color(0xFFF8F4FF))
    ) {
        AppHeader()

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

@Composable
fun ProductDetailsCard(product: Product?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Détails du produit",
                style = MaterialTheme.typography.headlineSmall.copy(color = Color(0xFF9C27B0)),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            product?.let {
                Image(
                    painter = painterResource(id = it.imageResId),
                    contentDescription = it.name,
                    modifier = Modifier
                        .size(220.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.height(16.dp))

                DetailRow("Nom", it.name)
                DetailRow("Prix", it.price)
                DetailRow("Description", it.description)
            } ?: Text(
                "Produit non trouvé.",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red)
            )
        }
    }
}

@Composable
fun BackButton(onBack: () -> Unit) {
    Button(
        onClick = onBack,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF9C27B0),
            contentColor = Color.White
        )
    ) {
        Text("Retour à la boutique", fontSize = 16.sp)
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "$label : ",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFF6A1B9A),
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.DarkGray),
            modifier = Modifier.weight(1f)
        )
    }
}