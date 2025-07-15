package com.yash.trigrexamassignment.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.yash.trigrexamassignment.data.local.AppDatabase
import com.yash.trigrexamassignment.data.local.RestaurantDao
import com.yash.trigrexamassignment.data.remote.FakeApiServer
import com.yash.trigrexamassignment.data.remote.RestaurantApi
import com.yash.trigrexamassignment.data.repository.RestaurantRepositoryImpl
import com.yash.trigrexamassignment.domain.repository.RestaurantRepository
import com.yash.trigrexamassignment.domain.use_case.GetRestaurantUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(baseUrl:String): RestaurantApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestaurantApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Application): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "room_db").build()

    @Provides
    @Singleton
    fun provideDao(db: AppDatabase): RestaurantDao = db.dao

    @Provides
    @Singleton
    fun getRestaurantRepository(
        restaurantApi: RestaurantApi,
        db: AppDatabase,
    ): RestaurantRepository {
        return RestaurantRepositoryImpl(restaurantApi, db)
    }

    @Provides
    @Singleton
    fun provideGetReaturantUseCase(restaurantRepository: RestaurantRepository): GetRestaurantUseCase {
        return GetRestaurantUseCase(restaurantRepository)
    }

    @Provides
    @Singleton
    fun provideBaseUrl(application: Application): String {
        return FakeApiServer.getBaseUrl()
    }
}