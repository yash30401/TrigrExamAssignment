package com.yash.trigrexamassignment.domain.use_case

import androidx.paging.PagingData
import com.yash.trigrexamassignment.domain.models.Restaurant
import com.yash.trigrexamassignment.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRestaurantUseCase @Inject constructor(
    private val restaurantRepository: RestaurantRepository,
) {
    suspend operator fun invoke(category: String): Flow<PagingData<Restaurant>> {
        return restaurantRepository.getRestaurants(category)
    }
}