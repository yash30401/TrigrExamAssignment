package com.yash.trigrexamassignment.domain.models

import com.yash.trigrexamassignment.data.remote.dto.RestaurantDto

data class RestaurantResponse(
    val location: String,
    val categories: List<Category>,
    val tabs: List<String>,
    val restaurants: List<RestaurantDto>
)


data class Category(
    val id: Int,
    val name: String,
    val icon: String
)

data class Restaurant(
    val id: Int,
    val name: String,
    val category: String,
    val rating: Double,
    val price_level: String,
    val delivery_time: String,
    val image_url: String
)