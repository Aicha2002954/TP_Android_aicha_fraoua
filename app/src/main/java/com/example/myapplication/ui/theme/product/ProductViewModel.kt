package com.example.myapplication.ui.theme.product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Entities.Product
import com.example.myapplication.data.repository.ProductRepository

class ProductViewModel : ViewModel() {

    private val repository = ProductRepository()

    var viewState by mutableStateOf(ProductViewState())
        private set

    fun onIntent(intent: ProductIntent) {
        when (intent) {
            is ProductIntent.LoadProducts -> loadProducts()
            is ProductIntent.Search -> search(intent.query)
        }
    }
    private fun loadProducts() {
        viewState = viewState.copy(isLoading = true)
        val products = repository.getProducts()
        viewState = viewState.copy(products = products, isLoading = false)
    }

    private fun search(query: String) {
        viewState = viewState.copy(isLoading = true)
        val filtered = repository.getProducts().filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true)
        }
        viewState = viewState.copy(products = filtered, isLoading = false)
    }

    fun getProductById(id: String): Product? = repository.getProductById(id)
}
