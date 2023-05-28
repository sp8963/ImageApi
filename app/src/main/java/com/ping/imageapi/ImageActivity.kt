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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ImageActivity : ComponentActivity(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = job
    private val job = Job()
    private val viewModel by viewModels<ImageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        launch(Dispatchers.Main) {
            viewModel.getAPI()
        }

        viewModel.imageInfoList.observe(this) {
            val recyclerView: RecyclerView = findViewById(R.id.recyclerview_display)
            launch(Dispatchers.Main) {
                viewModel.setAdapter(it, recyclerView)
            }

        }


    }
}