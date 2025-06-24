package com.example.myapplication.ui.theme.product.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.Entities.Product
import com.example.myapplication.ui.theme.product.ProductViewModel

fun getImageResource(productImage: String): Int {
    return when (productImage) {
        "image1" -> R.drawable.image1
        "image2" -> R.drawable.image2
        "image3" -> R.drawable.image3
        "image4" -> R.drawable.image4
        "image5" -> R.drawable.image5
        "image6" -> R.drawable.image6
        "chasseur1" -> R.drawable.chasseur1
        "chasseur2" -> R.drawable.chasseur2
        "chasseur3" -> R.drawable.chasseur3
        "chasseur4" -> R.drawable.chasseur4
        "fourinteur1" -> R.drawable.fourinteur1
        "fourinteur3" -> R.drawable.fourinteur3
        "garcon1" -> R.drawable.garcon1
        "garcon2" -> R.drawable.garcon2
        "garcon3" -> R.drawable.garcon3
        "garcon4" -> R.drawable.garcon4
        "joutes2" -> R.drawable.joutes2
        "payjma1" -> R.drawable.payjma1
        "payjma2" -> R.drawable.payjma2
        "payjma3" -> R.drawable.payjma3
        "payjma4" -> R.drawable.payjma4
        "payjma5" -> R.drawable.payjma5


        else -> R.drawable.image1
    }
}

@Composable
fun ProductItemComponent(
    product: Product,
    onClick: () -> Unit,
    viewModel: ProductViewModel,
    modifier: Modifier = Modifier
) {
    val isFavorite = viewModel.isFavorite(product)

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
            Box(modifier = Modifier.size(140.dp)) {
                Image(
                    painter = painterResource(id = getImageResource(product.imageResId)),
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                )
                IconButton(
                    onClick = { viewModel.toggleFavorite(product) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(28.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (isFavorite) "Retirer des favoris" else "Ajouter aux favoris",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFF6A1B9A)),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = product.price,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color(0xFF672037),
                        fontSize = 18.sp
                    ),
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row {
                repeat(5) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Star",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onClick,
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
