package com.yash.trigrexamassignment.data.remote

import android.content.Context
import com.google.gson.Gson
import com.yash.trigrexamassignment.domain.models.RestaurantResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import java.util.concurrent.TimeUnit

object FakeApiServer {
    private var mockWebServer: MockWebServer? = null
    private var baseUrl: String = ""
    private var allRestaurantsData: RestaurantResponse? = null // Store the parsed data

    // Use a flag to ensure init runs only once and prevent re-initialization issues
    private var isInitialized = false

    suspend fun init(context: Context) {
        if (isInitialized) return // Prevent multiple initializations

        withContext(Dispatchers.IO) {
            if (mockWebServer == null) {
                mockWebServer = MockWebServer()
                mockWebServer!!.start()
                baseUrl = mockWebServer!!.url("/").toString()
            }

            // Load all data once
            val allJson = context.assets.open("restaurants.json")
                .bufferedReader().use { it.readText() }
            allRestaurantsData = Gson().fromJson(allJson, RestaurantResponse::class.java)

            // Set up a dispatcher to handle requests dynamically
            mockWebServer!!.dispatcher = object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    // Extract category from the request URL if you want to mock filtering
                    // For now, let's just serve all data in chunks, simulating basic paging.
                    // A real API would have parameters like `category` and `page`

                    // In a real scenario, you would parse the request URL to get the category
                    // and page number, then filter/paginate `allRestaurantsData` accordingly.
                    // For this basic example, we'll just serve the whole dataset chunked.

                    // To simulate paging correctly for different categories, your API endpoint
                    // should include category and page parameters.
                    // e.g., /restaurants?category=Pizza&page=0

                    val path = request.path ?: "/"
                    val url = request.requestUrl

                    // Extract category from URL query parameters if they existed in your API design
                    val categoryParam = url?.queryParameter("category")

                    // Filter restaurants based on category if requested
                    val filteredRestaurants = if (categoryParam != null && categoryParam != "All") {
                        allRestaurantsData?.restaurants?.filter { it.category == categoryParam }
                    } else {
                        allRestaurantsData?.restaurants
                    }

                    // Simulate paging (this is a simplified approach, a real PagingSource would manage pages)
                    // For MockWebServer, you might need to manually keep track of pages
                    // or simplify for testing. A common pattern is to just return a chunk
                    // or the full dataset for a given category.

                    // For now, let's just return the *entire* filtered list for simplicity,
                    // as PagingSource will handle the chunking on its side by making requests
                    // until it runs out of items. If you want strict paging simulation here,
                    // you'd need to parse page parameters from the request.

                    val responseData = RestaurantResponse(
                        location = allRestaurantsData?.location ?: "",
                        categories = allRestaurantsData?.categories ?: emptyList(),
                        tabs = allRestaurantsData?.tabs ?: emptyList(),
                        restaurants = filteredRestaurants ?: emptyList()
                    )

                    val json = Gson().toJson(responseData)

                    return MockResponse()
                        .setResponseCode(200)
                        .setBody(json)
                        .setBodyDelay(100, TimeUnit.MILLISECONDS)
                }
            }
            isInitialized = true
        }
    }

    fun getBaseUrl(): String = baseUrl

    fun stop() {
        mockWebServer?.shutdown()
        mockWebServer = null
        isInitialized = false // Reset for potential re-initialization in tests
    }
}