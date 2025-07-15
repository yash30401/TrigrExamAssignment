package com.yash.trigrexamassignment.data.remote

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

object FakeApiServer {
     private var mockWebServer: MockWebServer? = null
    private var baseUrl: String = ""


    suspend fun init(context: Context) {
        withContext(Dispatchers.IO) {
            mockWebServer = MockWebServer()

            val json = context.assets.open("restaurants.json")
                .bufferedReader()
                .use { it.readText() }

            mockWebServer!!.enqueue(MockResponse().setResponseCode(200).setBody(json))
            mockWebServer!!.start()

            baseUrl = mockWebServer!!.url("/").toString()
        }
    }

    fun getBaseUrl(): String = baseUrl

    fun stop() {
        mockWebServer?.shutdown()
    }

}