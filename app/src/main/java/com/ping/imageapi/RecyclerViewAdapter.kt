package com.ping.imageapi

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerViewAdapter(var context: Context, private val batchSize: Int) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private var list: List<ImageInfo> = ArrayList()
    private var batchData = mutableListOf<ImageInfo>()
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvID: TextView = view.findViewById(R.id.tvID)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val igImage: ImageView = view.findViewById(R.id.imageView)
    }

    private fun loadNextBatch() {
        if (batchData.size == list.size) {
            Toast.makeText(context, "Data no more", Toast.LENGTH_SHORT).show()
            return
        }
        val startIndex = batchData.size
        val endIndex = minOf(startIndex + batchSize, list.size)
        batchData.addAll(list.subList(startIndex, endIndex))
        notifyDataSetChanged()
        Toast.makeText(context, "Data load more", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<ImageInfo>) {
        this.list = list
        val startIndex = batchData.size
        val endIndex = minOf(startIndex + batchSize, list.size)
        batchData.addAll(list.subList(startIndex, endIndex))
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
        )
    }


    override fun getItemCount(): Int {
        return batchData.size
    }

    fun loadMoreData() {
        loadNextBatch()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = list[position].title
        holder.tvID.text = list[position].id
        Glide.with(holder.itemView.context).load(list[position].thumbnailUrl).fitCenter().into(holder.igImage)
    }
}