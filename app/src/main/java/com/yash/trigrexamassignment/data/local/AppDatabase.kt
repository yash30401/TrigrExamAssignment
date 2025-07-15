package com.yash.trigrexamassignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RestaurantEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract val dao: RestaurantDao

}