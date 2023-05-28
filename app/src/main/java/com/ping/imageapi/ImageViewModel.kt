package com.ping.imageapi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

class ImageViewModel : ViewModel() {

    val imageInfoList: MutableLiveData<ArrayList<ImageInfo>> = MutableLiveData()

    suspend fun getAPI() {
        HttpModel.getImg(object : ApiCallback {
            override fun onSuccess(resp: String) {
                val list = ArrayList<ImageInfo>()
                val array = JSONArray(resp)
                val gson = Gson()
                for (i in 0 until array.length()) {
                    val a = array[i].toString()
                    list.add(gson.fromJson(a, ImageInfo::class.java))
                }
                imageInfoList.postValue(list)
            }

            override fun onError(resp: String) {
                val list = ArrayList<ImageInfo>()
                imageInfoList.postValue(list)
            }
        })

    }
}