
package com.example.myapplication.ui.theme.product.components

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


fun getImageResource(productImage: String): Int {
    return when (productImage) {
        "image1.jpg" -> R.drawable.image1
        "image2.jpg" -> R.drawable.image2
        "image3.jpg" -> R.drawable.image3
        "image4.jpg" -> R.drawable.image4
        "image5.jpg" -> R.drawable.image5
        "image6.jpg" -> R.drawable.image6
        "image7.jpg" -> R.drawable.image7
        "image8.jpg" -> R.drawable.image8
        "image9.jpg" -> R.drawable.image9
        "image10.jpg" -> R.drawable.image10
        else -> R.drawable.image1
    }
}


@Composable
fun ProductDetailsCard(
    product: Product?,
    onBack: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
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
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            product?.let {

                Image(
                    painter = painterResource(id = getImageResource(product.imageResId)),
                    contentDescription = product.name,
                    modifier = Modifier
                        .size(220.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))
                DetailRow("Nom", it.name)
                DetailRow("Prix", it.price)
                DetailRow("Quantité", it.quantity)
                DetailRow("Description", it.description)
            } ?: Text(
                "Produit non trouvé.",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}


@Composable
fun AppHeader(
    onProfileClick: () -> Unit = {}
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
                text = "Petit Papillon 🦋",
                style = MaterialTheme.typography.headlineLarge.copy(color = Color.White)
            )

            IconButton(onClick = onProfileClick) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profil utilisateur",
                    tint = Color.White
                )
            }
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
fun AppFooter() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFB667AD),
                        Color(0xFFBD98DE)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "© 2025 Petit Papillon",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }

}








