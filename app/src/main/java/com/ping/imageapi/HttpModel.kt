package com.ping.imageapi

import android.os.SystemClock
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import okhttp3.*
import java.io.IOException

object HttpModel {

    private fun getApi(url: String): String {
        Log.d("TAG", "sendGet $url ")
        var res = ""
        val client = OkHttpClient().newBuilder().build()
        val request = Request.Builder()
            .url(url)
            .build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                res = "Fail"
            }

            override fun onResponse(call: Call, response: Response) {
                res = response.body!!.string()
            }
        })//respond
        while (res.isEmpty()) {
            SystemClock.sleep(1)
            if (res.isNotEmpty()) break
        }
        return try {
            res
        } catch (e: Exception) {
            e.toString()
        }
    }

    suspend fun getImg(): String {
        return coroutineScope {
            withContext(Dispatchers.IO) {
                 getApi("https://jsonplaceholder.typicode.com/photos")
            }
        }
    }

}