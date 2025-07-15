package com.yash.trigrexamassignment

import android.app.Application
import android.content.res.Configuration
import com.yash.trigrexamassignment.data.remote.FakeApiServer
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking

@HiltAndroidApp
class TrigrExamApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Start mock server before Hilt kicks in
        runBlocking {
            FakeApiServer.init(this@TrigrExamApp)
        }
    }

}