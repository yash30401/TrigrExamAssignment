package com.yash.trigrexamassignment.domain.models

data class Restaurant(
    val id:Int,
    val name:String,
    val category:String,
    val rating: Double,
    val priceLevel: String,
    val deliveryTime: String,
    val imageUrl: String
)