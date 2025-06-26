
package com.example.myapplication.ui.theme.product.components
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Language
import androidx.compose.runtime.*

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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow

import androidx.navigation.NavController
import com.example.myapplication.ui.theme.product.ProductViewModel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R


@Composable
fun AppHeader(
    onLanguageSelected: (String) -> Unit
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

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

            Text(
                text = stringResource(id = R.string.little_butterfly),
                style = MaterialTheme.typography.headlineLarge.copy(color = Color.White)
            )

            IconButton(onClick = { showDialog = true }) {
                Icon(
                    imageVector = Icons.Filled.Language,
                    contentDescription = stringResource(id = R.string.choose_language),
                    tint = Color.White
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {},
            title = {
                Text(
                    text = stringResource(id = R.string.choose_language),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Column {
                    LanguageOption("Français", "fr") { onLanguageSelected(it); showDialog = false }
                    Divider()
                    LanguageOption("العربية", "ar") { onLanguageSelected(it); showDialog = false }
                    Divider()
                    LanguageOption("English", "en") { onLanguageSelected(it); showDialog = false }
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(20.dp)
        )
    }
}

@Composable
fun LanguageOption(name: String, code: String, onSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelected(code) }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Language,
            contentDescription = null,
            tint = Color(0xFFB45AAB),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = name, style = MaterialTheme.typography.bodyLarge)
    }
}


@Composable
fun FavoriteProductItemComponent(
    product: Product,
    viewModel: ProductViewModel,
    onClick: () -> Unit = {},
    onAddToCart: (String) -> Unit = {}
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
                onClick = {
                    val defaultSize = product.sizes.firstOrNull() ?: ""
                    onAddToCart(defaultSize)
                },
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
    offerPercent: Int?,
    onBack: () -> Unit,
    onAddToCartWithSize: (Product, String) -> Unit
) {
    var selectedSize by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(30.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                BackButton(onBack = onBack)
            }

            product?.let { it ->

                val originalPriceDouble = it.price
                    .removeSuffix("€")
                    .trim()
                    .replace(",", ".")
                    .toDoubleOrNull() ?: 0.0
                val discountedPriceDouble = offerPercent?.let { percent ->
                    originalPriceDouble * (100 - percent) / 100
                }

                fun formatPrice(price: Double): String =
                    String.format("%.2f€", price)

                Image(
                    painter = painterResource(id = getImageResource(it.imageResId)),
                    contentDescription = it.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF6A1B9A))
                    )
                    if (discountedPriceDouble != null) {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = formatPrice(originalPriceDouble),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color.Gray,
                                    textDecoration = TextDecoration.LineThrough
                                )
                            )
                            Text(
                                text = formatPrice(discountedPriceDouble),
                                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red)
                            )
                        }
                    } else {
                        Text(
                            text = it.price,
                            style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF6A1B9A))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (it.sizes.isNotEmpty()) {
                    Text(
                        text = stringResource(id = R.string.sizes_available),
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF6A1B9A))
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        it.sizes.forEach { size: String ->
                            val isSelected = selectedSize == size
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) Color(0xFF9C27B0) else Color(0xFFE1BEE7),
                                modifier = Modifier.clickable { selectedSize = size }
                            ) {
                                Text(
                                    text = size,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    color = if (isSelected) Color.White else Color.Black
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.in_stock,
                            it.quantity
                        ),
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF6A1B9A))
                    )

                    Button(
                        onClick = {
                            selectedSize?.let { size ->
                                onAddToCartWithSize(it, size)
                            }
                        },
                        enabled = selectedSize != null,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C27B0))
                    ) {
                        Text(
                            text = stringResource(id = R.string.add_to_cart),
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF3E5F5), RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = if (it.description.isNotBlank())
                            it.description
                        else
                            stringResource(id = R.string.no_description),
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                    )
                }
            } ?: Text(
                text = stringResource(id = R.string.product_not_found),
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
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
    val context = LocalContext.current

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
                    contentDescription = context.getString(R.string.search_product),
                    tint = Color(0xFF9C27B0)
                )
            },
            label = { Text(text = stringResource(id = R.string.search_product)) },
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
            keyboardActions = KeyboardActions(onSearch = {

            })
        )
    }
}


@Composable
fun AppFooter(navController: NavController, cartItemCount: Int) {
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
            IconButton(onClick = {
                if (navController.currentDestination?.route != "product") {
                    navController.navigate("product") {
                        launchSingleTop = true
                        popUpTo("product") { inclusive = false }
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Accueil",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            IconButton(onClick = {
                if (navController.currentDestination?.route != "favorites") {
                    navController.navigate("favorites") {
                        launchSingleTop = true
                        popUpTo("product") { inclusive = false }
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favoris",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Box {
                IconButton(onClick = {
                    if (navController.currentDestination?.route != "cart") {
                        navController.navigate("cart") {
                            launchSingleTop = true
                            popUpTo("product") { inclusive = false }
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Panier",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                if (cartItemCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(Color.Red, shape = CircleShape)
                            .align(Alignment.TopEnd)
                    ) {
                        Text(
                            text = cartItemCount.toString(),
                            color = Color.White,
                            fontSize = 10.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }


                    IconButton(onClick = {
                        if (navController.currentDestination?.route != "promo") {
                            navController.navigate("promo") {
                                launchSingleTop = true
                                popUpTo("product") { inclusive = false }
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Whatshot,
                            contentDescription = "Promotions",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }



            IconButton(onClick = {
                if (navController.currentDestination?.route != "login") {
                    navController.navigate("login") {
                        launchSingleTop = true
                        popUpTo("product") { inclusive = false }
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}



