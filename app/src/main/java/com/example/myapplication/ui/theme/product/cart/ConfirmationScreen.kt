package com.example.myapplication.ui.theme.product.cart

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.product.ProductViewModel
import com.example.myapplication.ui.theme.product.components.AppFooter
import com.example.myapplication.ui.theme.product.components.AppHeader
import com.example.myapplication.R

@DrawableRes
fun getDrawableResIdByName(context: Context, imageName: String): Int {
    return context.resources.getIdentifier(imageName, "drawable", context.packageName)
}

@Composable
fun ConfirmationScreen(
    navController: NavController,
    viewModel: ProductViewModel,
    onLanguageSelected: (String) -> Unit
) {
    val orderInfo = viewModel.orderInfo.value
    val orderItems = viewModel.orderItems
    val total = viewModel.getCartTotal()
    val context = LocalContext.current
    val cartItemCount = viewModel.cartItemCount

    val orderReceiptLabel = stringResource(id = R.string.order_receipt)
    val fullNameLabel = stringResource(id = R.string.full_name)
    val emailLabel = stringResource(id = R.string.email)
    val addressLabel = stringResource(id = R.string.address)
    val phoneLabel = stringResource(id = R.string.phone)
    val paymentMethodLabel = stringResource(id = R.string.payment_method)
    val yourItemsLabel = stringResource(id = R.string.your_items)
    val sizeLabel = stringResource(id = R.string.size)
    val quantityLabel = stringResource(id = R.string.quantity)
    val totalLabel = stringResource(id = R.string.total)

    Scaffold(
        topBar = { AppHeader(onLanguageSelected = onLanguageSelected) },
        bottomBar = { AppFooter(navController = navController, cartItemCount = cartItemCount) }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.logooo),
                    contentDescription = stringResource(id = R.string.order_success),
                    modifier = Modifier
                        .size(180.dp)
                        .padding(top = 12.dp)
                )
            }

            item {
                Text(
                    text = stringResource(id = R.string.order_thanks),
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.order_confirmation_sent),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = R.string.delivery_info),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        orderInfo?.let {
                            InfoRow("👤 $fullNameLabel", it.name)
                            InfoRow("📧 $emailLabel", it.email)
                            InfoRow("📍 $addressLabel", it.address)
                            InfoRow("📞 $phoneLabel", it.phone)
                            InfoRow("💳 $paymentMethodLabel", it.paymentMethod)
                        }
                    }
                }
            }

            item {
                Text(
                    text = "🛍️ $yourItemsLabel",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            items(orderItems) { orderItem ->
                val product = orderItem.product
                val drawableResId = getDrawableResIdByName(context, product.imageResId)

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = drawableResId),
                            contentDescription = product.name,
                            modifier = Modifier
                                .size(64.dp)
                                .padding(end = 12.dp)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(product.name, fontWeight = FontWeight.SemiBold)
                            Text("$sizeLabel : ${orderItem.size}", style = MaterialTheme.typography.bodySmall)
                            Text("$quantityLabel : ${orderItem.quantity}", style = MaterialTheme.typography.bodySmall)
                        }
                        Text(
                            "%.2f €".format(orderItem.price * orderItem.quantity),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            item {
                Divider()
                Text(
                    text = "💰 $totalLabel : %.2f €".format(total),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val content = buildString {
                            append("✅ $orderReceiptLabel\n\n")
                            orderInfo?.let {
                                append("👤 $fullNameLabel : ${it.name}\n")
                                append("📧 $emailLabel : ${it.email}\n")
                                append("📍 $addressLabel : ${it.address}\n")
                                append("📞 $phoneLabel : ${it.phone}\n")
                                append("💳 $paymentMethodLabel : ${it.paymentMethod}\n\n")
                            }
                            append("🛍️ $yourItemsLabel :\n")
                            orderItems.forEach {
                                append("- ${it.product.name} ($sizeLabel: ${it.size}) x${it.quantity} = %.2f €\n".format(it.price * it.quantity))
                            }
                            append("\n💰 $totalLabel : %.2f €".format(total))
                        }

                        generatePdfReceipt(context, "recu_commande", content)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(elevation = 6.dp, shape = RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = stringResource(id = R.string.download),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "PDF",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text("$label : ", fontWeight = FontWeight.Medium)
        Text(value)
    }
}
