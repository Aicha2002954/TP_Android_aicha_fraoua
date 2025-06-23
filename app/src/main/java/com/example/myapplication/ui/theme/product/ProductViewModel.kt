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

    //Partie PANIER
    private val _cart = mutableStateListOf<Product>()
    val cart: List<Product> get() = _cart

    fun addToCart(product: Product?) {
        product?.let {
            _cart.add(it)
        }
    }

    fun removeFromCart(product: Product) {
        _cart.remove(product)
    }

    fun clearCart() {
        _cart.clear()
    }

    fun getCartTotal(): Double {
        return _cart.sumOf { it.price.toDoubleOrNull() ?: 0.0 }
    }
    // Partie FAVORIS
    private val _favorites = mutableStateListOf<Product>()
    val favorites: List<Product> get() = _favorites

    fun toggleFavorite(product: Product) {
        if (_favorites.any { it.id == product.id }) {
            _favorites.removeAll { it.id == product.id }
        } else {
            _favorites.add(product)
        }
    }

    fun isFavorite(product: Product): Boolean {
        return _favorites.any { it.id == product.id }
    }
    // Fin Partie FAVORIS

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

    fun getProductById(productId: String): Product? {
        return viewState.products.find { it.id == productId }
    }
}
