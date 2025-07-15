package com.yash.trigrexamassignment

import android.app.Application
import android.content.res.Configuration
import com.yash.trigrexamassignment.data.remote.FakeApiServer
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@HiltAndroidApp
class TrigrExamApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Delay this using CoroutineScope tied to the lifecycle, or ensure `this` is not null
        CoroutineScope(Dispatchers.IO).launch {
            FakeApiServer.init(this@TrigrExamApp) // ✅ Pass non-null context
        }
    }

}