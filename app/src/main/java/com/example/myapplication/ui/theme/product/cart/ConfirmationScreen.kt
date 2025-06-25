package com.example.myapplication.ui.theme.product.cart

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.product.ProductViewModel
import com.example.myapplication.ui.theme.product.components.AppFooter
import com.example.myapplication.ui.theme.product.components.AppHeader

@DrawableRes
fun getDrawableResIdByName(context: Context, imageName: String): Int {
    return context.resources.getIdentifier(imageName, "drawable", context.packageName)
}

@Composable
fun ConfirmationScreen(
    navController: NavController,
    viewModel: ProductViewModel
) {
    val orderInfo = viewModel.orderInfo.value
    val orderItems = viewModel.orderItems
    val total = viewModel.getCartTotal()
    val context = LocalContext.current
    val cartItemCount = viewModel.cartItemCount

    Scaffold(
        topBar = { AppHeader() },
        bottomBar = { AppFooter(navController = navController, cartItemCount = cartItemCount) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "✅ Commande confirmée",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            orderInfo?.let {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("👤 Nom : ${it.name}")
                        Text("📧 Email : ${it.email}")
                        Text("📍 Adresse : ${it.address}")
                        Text("📞 Téléphone : ${it.phone}")
                        Text("💳 Paiement : ${it.paymentMethod}")
                    }
                }
            }

            Text(
                text = "🛍️ Détails de la commande",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    orderItems.forEach { orderItem ->
                        val product = orderItem.product
                        val quantity = orderItem.quantity
                        val size = orderItem.size
                        val price = orderItem.price

                        val drawableResId = getDrawableResIdByName(context, product.imageResId)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = drawableResId),
                                contentDescription = product.name,
                                modifier = Modifier
                                    .size(56.dp)
                                    .padding(end = 12.dp)
                            )

                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = product.name, style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    text = "Taille : $size",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "Quantité : $quantity",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Text(
                                text = "%.2f €".format(price * quantity),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Divider()
                    }
                }
            }

            Divider()

            Text(
                text = "💰 Total : %.2f €".format(total),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val content = buildString {
                        append("✅ Reçu de Commande\n\n")
                        orderInfo?.let {
                            append("👤 Nom : ${it.name}\n")
                            append("📧 Email : ${it.email}\n")
                            append("📍 Adresse : ${it.address}\n")
                            append("📞 Téléphone : ${it.phone}\n")
                            append("💳 Paiement : ${it.paymentMethod}\n\n")
                        }
                        append("🛍️ Articles commandés :\n")
                        orderItems.forEach {
                            val price = it.price  // ✅ ici aussi
                            append("- ${it.product.name} (Taille: ${it.size}) x${it.quantity} = %.2f €\n".format(price * it.quantity))
                        }
                        append("\n💰 Total : %.2f €".format(total))
                    }

                    generatePdfReceipt(context, "recu_commande", content)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Default.Download, contentDescription = "Télécharger")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Télécharger le reçu", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}
