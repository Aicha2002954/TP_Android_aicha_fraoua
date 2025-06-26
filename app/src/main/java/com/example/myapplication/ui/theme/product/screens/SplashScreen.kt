package com.example.myapplication.ui.theme.product.screens
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
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
        "🌸 Petit Papillon, pour vos petits trésors.",
        "💖 Le style dès les premiers pas."
    )
    val images = listOf(
        R.drawable.image4,
        R.drawable.garcon3,
        R.drawable.joutes2
    )

    var currentIndex by remember { mutableStateOf(0) }
    val fadeAnim = remember { Animatable(0f) }
    val pulseAnim = rememberInfiniteTransition()
    val pulse by pulseAnim.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    LaunchedEffect(Unit) {
        while (currentIndex < phrases.size) {
            fadeAnim.snapTo(0f)
            fadeAnim.animateTo(1f, tween(800))
            delay(1200)
            currentIndex++
        }
        delay(1000)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFFFF8F0), Color(0xFFFFE3F3))
                )
            ),
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
                        .size(280.dp)
                        .scale(pulse)
                        .alpha(fadeAnim.value)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (currentIndex < phrases.size) {
                Text(
                    text = phrases[currentIndex],
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF9B59B6),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .alpha(fadeAnim.value)
                        .padding(horizontal = 24.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Des vêtements tendres pour les moments les plus précieux.",
                fontSize = 14.sp,
                color = Color(0xFF7F8C8D),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(36.dp))

            Row(horizontalArrangement = Arrangement.Center) {
                repeat(3) { i ->
                    val dotAlpha = remember { Animatable(0f) }
                    LaunchedEffect(i, currentIndex) {
                        delay(i * 300L)
                        dotAlpha.animateTo(1f, tween(300))
                        dotAlpha.animateTo(0.2f, tween(300))
                    }

                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .padding(4.dp)
                            .background(
                                Color(0xFFBA68C8).copy(alpha = dotAlpha.value),
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}
