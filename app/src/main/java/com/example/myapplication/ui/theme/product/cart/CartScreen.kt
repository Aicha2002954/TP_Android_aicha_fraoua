package com.example.myapplication.ui.theme.product.cart

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.data.Entities.Product
import com.example.myapplication.ui.theme.product.ProductViewModel
import com.example.myapplication.ui.theme.product.components.AppFooter
import com.example.myapplication.ui.theme.product.components.AppHeader
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@Composable
fun CartScreen(
    viewModel: ProductViewModel,
    navController: NavController,
    onBack: () -> Unit,
    onLanguageSelected: (String) -> Unit
) {
    val cart = viewModel.cart
    val total = viewModel.getCartTotal()
    val cartItemCount = viewModel.cartItemCount
    val context = LocalContext.current

    val selectedItems = remember { mutableStateMapOf<String, Boolean>() }

    val emptyCartMessage = stringResource(id = R.string.cart_empty_message)
    val totalLabel = "Total : %.2f €".format(total)
    val selectAtLeastOneItemMessage = stringResource(id = R.string.select_at_least_one_item)
    val orderButtonText = stringResource(id = R.string.order_button)

    Scaffold(
        topBar = {
            AppHeader(onLanguageSelected = onLanguageSelected)
        },
        bottomBar = {
            AppFooter(navController = navController, cartItemCount = cartItemCount)
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (cart.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(emptyCartMessage, style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(cart) { cartItem ->
                        val key = "${cartItem.product.id}_${cartItem.size}"
                        CartItem(
                            product = cartItem.product,
                            quantity = cartItem.quantity,
                            size = cartItem.size,
                            unitPrice = cartItem.price,
                            isSelected = selectedItems[key] == true,
                            onSelectionChange = { isChecked -> selectedItems[key] = isChecked },
                            onQuantityChange = { newQty ->
                                viewModel.updateQuantity(cartItem.product, cartItem.size, newQty)
                            },
                            onRemove = {
                                viewModel.removeFromCart(cartItem.product, cartItem.size)
                                selectedItems.remove(key)
                            },
                            onProductClick = {
                                navController.navigate("details/${cartItem.product.id}")
                            }
                        )
                        Divider()
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = totalLabel,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val selectedProducts = cart.filter {
                            val key = "${it.product.id}_${it.size}"
                            selectedItems[key] == true
                        }

                        if (selectedProducts.isEmpty()) {
                            Toast.makeText(
                                context,
                                selectAtLeastOneItemMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (viewModel.isUserLoggedIn) {
                                viewModel.setOrderItems(selectedProducts)
                                navController.navigate("orderForm")
                            } else {
                                navController.navigate("login")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C27B0))
                ) {
                    Text(orderButtonText, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun CartItem(
    product: Product,
    quantity: Int,
    size: String,
    unitPrice: Double,
    isSelected: Boolean,
    onSelectionChange: (Boolean) -> Unit,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit,
    onProductClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = onSelectionChange
        )

        Spacer(modifier = Modifier.width(8.dp))

        val context = LocalContext.current
        val resId = context.resources.getIdentifier(product.imageResId, "drawable", context.packageName)
        val painter = if (resId != 0) painterResource(id = resId) else painterResource(id = R.drawable.image2)

        Image(
            painter = painter,
            contentDescription = product.name,
            modifier = Modifier
                .size(64.dp)
                .clickable { onProductClick() },
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text(text = product.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Catégorie : ${product.category}", style = MaterialTheme.typography.bodySmall)
            Text(
                text = "Taille : $size",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { if (quantity > 1) onQuantityChange(quantity - 1) }) {
                    Icon(Icons.Default.Remove, contentDescription = "Moins")
                }

                Text(text = quantity.toString(), style = MaterialTheme.typography.bodyLarge)

                IconButton(onClick = { onQuantityChange(quantity + 1) }) {
                    Icon(Icons.Default.Add, contentDescription = "Plus")
                }
            }

            Text(
                text = "%.2f €".format(unitPrice * quantity),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        IconButton(onClick = onRemove) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Supprimer",
                tint = Color.Red
            )
        }
    }
}
