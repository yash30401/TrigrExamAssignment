package com.yash.trigrexamassignment.domain.repository

import androidx.paging.PagingData
import com.yash.trigrexamassignment.domain.models.Restaurant
import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {
    fun getRestaurants(category: String): Flow<PagingData<Restaurant>>
}