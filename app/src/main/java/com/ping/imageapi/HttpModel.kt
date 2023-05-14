package com.ping.imageapi

import android.os.SystemClock
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import okhttp3.*
import java.io.IOException
import java.lang.Exception

object HttpModel {

    private fun getApi(url: String): String {
        Log.d("TAG", "sendGet $url ")
        var res = ""
        val client = OkHttpClient().newBuilder().build()
        try {
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            res = response.body!!.string()
        } catch (err: Exception) {
            return "Fail"
        }
        return res
    }

    suspend fun getImg(): String {
        return coroutineScope {
            getApi("https://jsonplaceholder.typicode.com/photos")
        }
    }

}