package com.yash.trigrexamassignment.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.yash.trigrexamassignment.data.local.AppDatabase
import com.yash.trigrexamassignment.data.remote.RestaurantApi
import com.yash.trigrexamassignment.data.remote.dto.toDomain
import com.yash.trigrexamassignment.domain.models.Restaurant
import com.yash.trigrexamassignment.domain.models.RestaurantResponse
import com.yash.trigrexamassignment.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor(
    private val api: RestaurantApi,
    private val db: AppDatabase,
) : RestaurantRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getRestaurants(category: String): Flow<PagingData<Restaurant>> {
        val pagingSourceFactory = {
            db.dao.getRestaurantsPaging(category)
        }

        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = RestaurantRemoteMediator(category, api, db),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }
}