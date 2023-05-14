package com.ping.imageapi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ImageActivity: ComponentActivity() {
    private lateinit var adapter: RecyclerViewAdapter
    private val viewModel by viewModels<ImageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerview_display)
        recyclerView.layoutManager = GridLayoutManager(this, 4)
        adapter = RecyclerViewAdapter(this)
        recyclerView.adapter = adapter


        viewModel.getAPI()

        viewModel.imageInfoList.observe(this) {
            if (it.size > 0) {
                adapter.setData(it)
                Toast.makeText(this,"Data Set", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"No data", Toast.LENGTH_SHORT).show()
            }
        }


    }
}