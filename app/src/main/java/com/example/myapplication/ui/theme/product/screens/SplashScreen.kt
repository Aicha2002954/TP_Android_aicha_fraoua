package com.example.myapplication.ui.theme.product.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay
import com.example.myapplication.R

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }
    val scale = remember { androidx.compose.animation.core.Animatable(0f) }

    // ✨ Phrases mzyanin pour boutique bébé
    val phrases = listOf(
        "🍼 La douceur commence ici...",
        "👶 Des habits faits avec amour.",
        "🌸 Petit Papillon, pour vos petits trésors.",
        "💖 Le style dès les premiers pas."
    )
    var currentPhrase by remember { mutableStateOf(phrases[0]) }

    // Animation changement de phrase
    LaunchedEffect(true) {
        startAnimation = true
        scale.animateTo(
            targetValue = 1f,
            animationSpec = androidx.compose.animation.core.tween(1000)
        )

        for (i in 1 until phrases.size) {
            delay(900)
            currentPhrase = phrases[i]
        }

        delay(1000)
        onTimeout()
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8F0)), // couleur pastel douce
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.image2),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(300.dp)
                    .scale(scale.value)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = currentPhrase,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF8E44AD), // couleur douce violette
                textAlign = TextAlign.Center
            )

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

