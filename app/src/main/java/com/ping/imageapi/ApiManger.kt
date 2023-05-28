package com.ping.imageapi

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiManger {
    private val httpClient: OkHttpClient by lazy {
        val httpClient = OkHttpClient.Builder()
        httpClient.callTimeout(30, TimeUnit.SECONDS)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.writeTimeout(30, TimeUnit.SECONDS)
        httpClient.build()
    }

    private val apiServer: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
        retrofit.create(ApiService::class.java)
    }

    /**
     * 呼叫api > get data
     */
    fun getImage(callback: ApiCallback?) {
        apiServer.getImage()
            .let { if (callback != null) it.enqueue(getApiCallback(callback)) }
    }

    /**
     * 取得 API Callback
     */
    private fun getApiCallback(callback: ApiCallback): Callback<ResponseBody> {
        return object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody>) {
                onApiResponse(response, callback)
            }

            override fun onFailure(call: Call<ResponseBody?>, throwable: Throwable) {
                onApiFailure(throwable, callback)
            }
        }
    }

    /**
     * 處理API回傳Response
     */
    private fun onApiResponse(
        response: Response<ResponseBody>,
        callback: ApiCallback
    ) {
        //200的回傳資訊在body內，非200的要抓errorBody
        val isHttpOK = response.code() == 200
        val resp = if (isHttpOK) response.body()!!.string() else response.errorBody()!!.string()

        if (isHttpOK) {
            callback.onSuccess(resp)
        } else{
            callback.onError(resp)
        }
    }

    /**
     * 處理API呼叫失敗
     */
    private fun onApiFailure(throwable: Throwable, callback: ApiCallback) {
        val httpMessage = throwable.message ?: ""
        callback.onError(httpMessage)
    }
}