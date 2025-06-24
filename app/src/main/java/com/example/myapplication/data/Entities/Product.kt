package com.example.myapplication.data.Entities



import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("quantity")
    val quantity: String,
    @SerializedName("couleur")
    val couleur: String,
    @SerializedName("imageResId")
    val imageResId: String,
    @SerializedName("category")
    val category: String,

    @SerializedName("sizes")
    val sizes: List<String> = emptyList()
)
