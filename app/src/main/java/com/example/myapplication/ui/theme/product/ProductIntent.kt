package com.example.myapplication.ui.theme.product

sealed class ProductIntent {
    data class Search(val query: String) : ProductIntent()
    object LoadProducts : ProductIntent()
}
