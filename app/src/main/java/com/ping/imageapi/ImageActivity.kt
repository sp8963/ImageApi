package com.ping.imageapi

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

        launch(Dispatchers.Main) {
            viewModel.getAPI()
        }

        viewModel.imageInfoList.observe(this) {
            launch(Dispatchers.Main) {
                if (it.size > 0) {
                    val recyclerView: RecyclerView = findViewById(R.id.recyclerview_display)
                    recyclerView.layoutManager = GridLayoutManager(this@ImageActivity, 4)
                    adapter = RecyclerViewAdapter(this@ImageActivity, 100)
                    recyclerView.adapter = adapter

                    recyclerView.addOnScrollListener(object : OnScrollListener(){
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            if (!recyclerView.canScrollVertically(1)) {
                                adapter.loadMoreData();
                            }
                        }
                    })
                    adapter.setData(it)
                    Toast.makeText(this@ImageActivity,"Data Set", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ImageActivity,"No data", Toast.LENGTH_SHORT).show()
                }
            }

        }


    }
}