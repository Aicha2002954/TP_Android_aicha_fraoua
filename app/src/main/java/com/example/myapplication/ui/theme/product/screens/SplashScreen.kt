package com.example.myapplication.ui.theme.product.screens
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import kotlinx.coroutines.delay
@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    val phrases = listOf(
        "🍼 La douceur commence ici...",
        "👶 Des habits faits avec amour.",
        "🌸 Petit Papillon, pour vos petits trésors.",
        "💖 Le style dès les premiers pas."
    )
    val images = listOf(
        R.drawable.image2,
        R.drawable.fourinteurlogo,
        R.drawable.garcon3,
        R.drawable.joutes2
    )

    var currentIndex by remember { mutableStateOf(0) }
    val alphaAnim = remember { Animatable(1f) }
    val scale = remember { androidx.compose.animation.core.Animatable(0f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )
        while (currentIndex < phrases.size) {
            alphaAnim.animateTo(0f, tween(400))
            currentIndex++
            if (currentIndex >= phrases.size) break

            alphaAnim.animateTo(1f, tween(400))

            delay(900)
        }
        delay(1000)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8F0)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (currentIndex < images.size) {
                Image(
                    painter = painterResource(id = images[currentIndex]),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(350.dp)
                        .scale(scale.value)
                        .alpha(alphaAnim.value)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            if (currentIndex < phrases.size) {
                Text(
                    text = phrases[currentIndex],
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF8E44AD),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.alpha(alphaAnim.value)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Des vêtements tendres pour les moments les plus précieux.",
                fontSize = 14.sp,
                color = Color(0xFF555555),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
    }
}
