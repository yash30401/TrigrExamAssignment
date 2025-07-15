package com.yash.trigrexamassignment.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "restaurants")
data class RestaurantEntity(
   @PrimaryKey val id:Int,
    val name: String,
    val category: String,
    val rating: Double,
    val priceLevel: String,
    val deliveryTime: String,
    val imageUrl: String
)