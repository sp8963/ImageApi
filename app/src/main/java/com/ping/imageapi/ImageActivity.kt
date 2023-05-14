package com.ping.imageapi

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ImageActivity : ComponentActivity(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = job
    private val job = Job()
    private lateinit var adapter: RecyclerViewAdapter
    private val viewModel by viewModels<ImageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerview_display)
        recyclerView.layoutManager = GridLayoutManager(this, 4)
        adapter = RecyclerViewAdapter(this, 100)
        recyclerView.adapter = adapter
        val tvMore: TextView = findViewById(R.id.tvMore)
        tvMore.setOnClickListener {
            adapter.loadMoreData()
        }

        launch(Dispatchers.Main) {
            viewModel.getAPI()
        }

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