package com.example.myapplication.ui.theme.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.myapplication.data.repository.ProductRepository
import com.example.myapplication.data.Entities.Product
import kotlinx.coroutines.launch
import androidx.compose.runtime.*

data class Offer(
    val productId: String,
    val discountPercent: Int
)

class CartItemData(
    val product: Product,
    var size: String,
    quantity: Int = 1,
    val price: Double
) {
    var quantity by mutableStateOf(quantity)
}

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    var viewState by mutableStateOf(ProductViewState())
        private set

    private val _cart = mutableStateListOf<CartItemData>()
    val cart: List<CartItemData> get() = _cart

    private val _favorites = mutableStateListOf<Product>()
    val favorites: List<Product> get() = _favorites

    private val _orderItems = mutableStateListOf<CartItemData>()
    val orderItems: List<CartItemData> get() = _orderItems

    private val _orderInfo = mutableStateOf<OrderInfo?>(null)
    val orderInfo: State<OrderInfo?> = _orderInfo

    private var _currentUser: String? = null
    val currentUser: String? get() = _currentUser
    val isUserLoggedIn: Boolean get() = _currentUser != null

    val offers = listOf(
        Offer("1", 20),
        Offer("5", 10),
        Offer("2", 30),
        Offer("6", 25),
        Offer("17", 15)
    )

    fun loginUser(userId: String) {
        _currentUser = userId
    }

    fun logoutUser() {
        _currentUser = null
    }

    fun getOfferForProduct(productId: String): Offer? {
        return offers.find { it.productId == productId }
    }

    fun saveOrderInfo(name: String, email: String, address: String, phone: String, paymentMethod: String) {
        _orderInfo.value = OrderInfo(name, email, address, phone, paymentMethod)
    }

    fun addToCartWithSizeAndPrice(product: Product, size: String, price: Double) {
        val existingItem = _cart.find { it.product.id == product.id && it.size == size }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            _cart.add(CartItemData(product, size, 1, price))
        }
    }

    val cartItemCount: Int get() = _cart.sumOf { it.quantity }

    fun removeFromCart(product: Product, size: String) {
        _cart.removeAll { it.product.id == product.id && it.size == size }
    }

    fun clearCart() {
        _cart.clear()
    }

    fun updateQuantity(product: Product, size: String, newQuantity: Int) {
        val item = _cart.find { it.product.id == product.id && it.size == size }
        if (item != null) {
            if (newQuantity <= 0) {
                removeFromCart(product, size)
            } else {
                item.quantity = newQuantity
            }
        }
    }

    fun getCartTotal(): Double {
        return cart.sumOf { it.price * it.quantity }
    }

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
                        it.description.contains(query, ignoreCase = true) ||
                        it.category.contains(query, ignoreCase = true)
            }
            viewState = viewState.copy(products = filtered, isLoading = false)
        }
    }

    fun getProductById(productId: String): Product? {
        return viewState.products.find { it.id == productId }
    }

    fun setOrderItems(items: List<CartItemData>) {
        _orderItems.clear()
        _orderItems.addAll(items)
    }

    data class OrderInfo(
        val name: String,
        val email: String,
        val address: String,
        val phone: String,
        val paymentMethod: String
    )
}
