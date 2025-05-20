package com.example.myapplication.ui.theme.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.myapplication.data.repository.ProductRepository
import com.example.myapplication.data.Entities.Product
import kotlinx.coroutines.launch
import androidx.compose.runtime.*

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    var viewState by mutableStateOf(ProductViewState())
        private set

    fun onIntent(intent: ProductIntent) {
        when (intent) {
            is ProductIntent.LoadProducts -> loadProducts()
            is ProductIntent.Search -> search(intent.query)
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            viewState = viewState.copy(isLoading = true)
            val products = repository.getProducts()
            viewState = viewState.copy(products = products, isLoading = false)
        }
    }

    private fun search(query: String) {
        viewModelScope.launch {
            viewState = viewState.copy(isLoading = true)
            val filtered = repository.getProducts().filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
            viewState = viewState.copy(products = filtered, isLoading = false)
        }
    }

    fun getProductById(id: String): Product? = repository.getProductById(id)
}
