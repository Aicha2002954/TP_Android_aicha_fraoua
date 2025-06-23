package com.example.myapplication.data.repository

import android.util.Log
import com.example.myapplication.R
import com.example.myapplication.data.Api.ProductApi
import com.example.myapplication.data.Entities.Product
import jakarta.inject.Inject
import kotlinx.coroutines.delay




class ProductRepository @Inject constructor(

        private val api: ProductApi
    ) {
    suspend fun getProducts(): List<Product> {
        val products = api.getProducts()

        Log.d("ProductRepository", "Loaded products: ${products.size}")
        return products
    }
    }
