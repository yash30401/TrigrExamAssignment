package com.yash.trigrexamassignment.data.remote.dto

import com.yash.trigrexamassignment.data.local.RestaurantEntity
import com.yash.trigrexamassignment.domain.models.Restaurant

data class RestaurantDto(
    val id: Int,
    val name: String,
    val category: String,
    val rating: Double,
    val price_level: String,
    val delivery_time: String,
    val image_url: String,
)

fun RestaurantDto.toEntity(forCategory: String) = RestaurantEntity(
    id = id,
    name = name,
    category = forCategory, // override the category to match tab/category from UI
    rating = rating,
    priceLevel = price_level,
    deliveryTime = delivery_time,
    imageUrl = image_url
)

fun RestaurantEntity.toDomain() = Restaurant(
    id, name, category, rating, priceLevel, deliveryTime, imageUrl
)