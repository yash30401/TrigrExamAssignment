package com.yash.trigrexamassignment.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RestaurantDao {

    @Query("SELECT * FROM restaurants WHERE category = :category ORDER BY rating DESC")
    fun getRestaurantsPaging(category: String): PagingSource<Int, RestaurantEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(restaurantEntity: List<RestaurantEntity>)

    @Query("DELETE FROM restaurants WHERE category = :category")
    suspend fun clearCategory(category: String)
}