package com.yash.trigrexamassignment.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.yash.trigrexamassignment.data.local.AppDatabase
import com.yash.trigrexamassignment.data.local.RestaurantEntity
import com.yash.trigrexamassignment.data.remote.RestaurantApi
import com.yash.trigrexamassignment.data.remote.dto.toEntity
import java.lang.Exception

@OptIn(ExperimentalPagingApi::class)
class RestaurantRemoteMediator(
    private val category: String,
    private val restaurantApi: RestaurantApi,
    private val db: AppDatabase,
) : RemoteMediator<Int, RestaurantEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RestaurantEntity>,
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> (state.pages.lastOrNull()?.data?.size ?: 0) / state.config.pageSize + 1
        }

        return try {
            val response = restaurantApi.getRestaurants(category, page, state.config.pageSize)
            val entities = response.restaurants.map { it.toEntity(category) }


            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.dao.clearCategory(category)
                }
                db.dao.insertAll(entities)
            }

            MediatorResult.Success(endOfPaginationReached = entities.isEmpty())

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}