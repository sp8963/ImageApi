package com.ping.imageapi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONArray

class ImageViewModel: ViewModel() {

    val imageInfoList: MutableLiveData<ArrayList<ImageInfo>> = MutableLiveData()

    suspend fun getAPI(){
        coroutineScope {
            withContext(Dispatchers.IO) {
                val getImg = HttpModel.getImg()
                val list = ArrayList<ImageInfo>()
                if (getImg != "Fail") {
                    val array = JSONArray(getImg)
                    val gson = Gson()
                    for (i in 0 until array.length()) {
                        val a = array[i].toString()
                        list.add(gson.fromJson(a, ImageInfo::class.java))
                    }
                }
                imageInfoList.postValue(list)
            }
        }
    }
}