package com.yash.trigrexamassignment.data.remote

import com.yash.trigrexamassignment.data.remote.dto.RestaurantDto
import com.yash.trigrexamassignment.domain.models.RestaurantResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantApi {
    @GET("/")
    suspend fun getRestaurants(
        @Query("category") category: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): RestaurantResponse

}