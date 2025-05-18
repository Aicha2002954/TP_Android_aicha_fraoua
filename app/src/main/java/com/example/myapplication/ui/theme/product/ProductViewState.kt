package com.example.myapplication.ui.theme.product

import com.example.myapplication.data.Entities.Product

data class ProductViewState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
