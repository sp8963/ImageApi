package com.ping.imageapi

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
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

    fun setAdapter(it : ArrayList<ImageInfo>, recyclerView:RecyclerView){
        if (it.size > 0) {
            recyclerView.layoutManager = GridLayoutManager(recyclerView.context, 4)
            val adapter = RecyclerViewAdapter(recyclerView.context, 100)
            recyclerView.adapter = adapter

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) {
                        adapter.loadMoreData();
                    }
                }
            })
            adapter.setData(it)
            Toast.makeText(recyclerView.context,"Data Set", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(recyclerView.context,"No data", Toast.LENGTH_SHORT).show()
        }
    }
}