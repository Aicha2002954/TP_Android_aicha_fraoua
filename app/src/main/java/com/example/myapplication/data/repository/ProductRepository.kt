package com.example.myapplication.data.repository

import com.example.myapplication.R
import com.example.myapplication.data.Entities.Product
import jakarta.inject.Inject

class ProductRepository @Inject constructor() {
    private val products = listOf(
        Product("1", "Jean slim", "99€", "Un jean slim bleu clair", R.drawable.image7),
        Product("2", "Robe été", "120€", "Une robe légère pour l'été", R.drawable.image8),
        Product("3", "Veste cuir", "250€", "Veste en cuir noir élégante", R.drawable.image9),
        Product("4", "T-shirt oversize", "45€", "T-shirt blanc oversize pour un look décontracté", R.drawable.image10),
        Product("5", "Pantalon cargo", "80€", "Pantalon cargo avec plusieurs poches", R.drawable.image1),
        Product("6", "Pull en laine", "110€", "Pull chaud en laine pour l'hiver", R.drawable.image2),
        Product("7", "Chemise en jean", "75€", "Chemise décontractée en denim", R.drawable.image3),
        Product("8", "Blazer homme", "180€", "Blazer élégant pour les occasions formelles", R.drawable.image4),
        Product("9", "Combinaison femme", "140€", "Combinaison chic pour femme", R.drawable.image5),
        Product("10", "Jupe longue", "95€", "Jupe longue fluide pour l'été", R.drawable.image6)
    )

    fun getProducts(): List<Product> = products
    fun getProductById(id: String): Product? = products.find { it.id == id }
}
