package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myapplication.navigator.AppNavigation
import com.example.myapplication.ui.theme.product.ProductViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ProductViewModel()
        setContent {
            AppNavigation(viewModel = viewModel)
        }
    }
}
