package com.ping.imageapi

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("photos")
    fun getImage():  Call<ResponseBody>

}