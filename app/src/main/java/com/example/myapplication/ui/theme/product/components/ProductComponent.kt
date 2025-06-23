
package com.example.myapplication.ui.theme.product.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search

import com.example.myapplication.ui.theme.product.components.BackButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.Entities.Product
import androidx.compose.material3.IconButton

import androidx.compose.material3.*
import com.example.myapplication.R

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.product.ProductViewModel


import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.IconButton

import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.IconButton

@Composable
fun FavoriteProductItemComponent(
    product: Product,
    viewModel: ProductViewModel,
    onClick: () -> Unit = {},
    onAddToCart: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFBEAFF)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.toggleFavorite(product) },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Supprimer des favoris",
                    tint = Color.Red
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                painter = painterResource(id = getImageResource(product.imageResId)),
                contentDescription = product.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onClick() }
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onClick() }
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFF6A1B9A)),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${product.price} ",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF9C27B0))
                )
                Text(
                    text = "${product.quantity} en stock",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
            }

            IconButton(
                onClick = onAddToCart,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.AddShoppingCart,
                    contentDescription = "Ajouter au panier",
                    tint = Color(0xFF9C27B0)
                )
            }
        }
    }
}



@Composable
fun ProductDetailsCard(
    product: Product?,
    onBack: () -> Unit,
    onAddToCart: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                BackButton(onBack = onBack)
            }

            Text(
                text = "Détails du produit",
                style = MaterialTheme.typography.headlineSmall.copy(color = Color(0xFF9C27B0)),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            product?.let {

                Image(
                    painter = painterResource(id = getImageResource(product.imageResId)),
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        ProductInfoRow(value = "\uD83D\uDECD\uFE0F " + it.name)
                        ProductInfoRow(value = "\uD83D\uDCB0 " + it.price)
                        ProductInfoRow(value = "\uD83D\uDCE6 " + it.quantity + " en stock")
                        ProductInfoRow(value = "\uD83D\uDD14 Catégorie : " + it.category)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = onAddToCart,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C27B0))
                    ) {
                        Text("Ajouter au panier", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF3E5F5), RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {

                    Text(
                        text = if (it.description.isNotBlank()) it.description else "Aucune description disponible",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                    )
                }

            } ?: Text(
                "Produit non trouvé.",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun ProductInfoRow( value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF6A1B9A))
        )
    }
}


@Composable
fun AppHeader(

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFB45AAB),
                        Color(0xFFBD98DE)
                    )
                )
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(48.dp))

            Text(
                text = "\uD83E\uDD8B Petit Papillon ",
                style = MaterialTheme.typography.headlineLarge.copy(color = Color.White)
            )
        }
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
                color = Color(0xFF9C27B0),
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


@Composable
fun BackButton(onBack: () -> Unit) {
    IconButton(
        onClick = onBack,
        modifier = Modifier.padding(2.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Retour",
            tint = Color(0xFF9C27B0)
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
    viewModel: ProductViewModel,
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
        items(products) { product: Product ->
            ProductItemComponent(
                product = product,
                viewModel = viewModel,
                onClick = { onNavigateToDetails(product.id) }
            )
        }
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
fun AppFooter(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFB667AD),
                        Color(0xFFBD98DE)
                    )
                )
            )
            .padding(vertical = 8.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Accueil",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
            IconButton(onClick = { navController.navigate("favorites") }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favoris",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
            IconButton(onClick = { navController.navigate("cart") }) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Panier",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
            IconButton(onClick = { navController.navigate("profile") }) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profil",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
            IconButton(onClick = { navController.navigate("login") }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Connexion",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}
