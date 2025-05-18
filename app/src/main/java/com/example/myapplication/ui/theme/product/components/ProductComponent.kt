
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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



