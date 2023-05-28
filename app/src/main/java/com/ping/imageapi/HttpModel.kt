package com.ping.imageapi

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object HttpModel {

    suspend fun getImg(callback: ApiCallback) {
        return withContext(Dispatchers.IO) {
            ApiManger().getImage(callback)
        }
    }

}