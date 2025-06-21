package com.example.myapplication.data.Entities



import com.google.gson.annotations.SerializedName


data class Product(
    @SerializedName("id")
    val id: String,
    @SerializedName(" name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("quantity")
    val quantity: String,
    @SerializedName("imageResId")
    val imageResId: String,


)