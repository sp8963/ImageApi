package com.ping.imageapi

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerViewAdapter(var context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private var list: List<ImageInfo> = ArrayList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.textView_Title)
        val tvPrice: TextView = view.findViewById(R.id.textView_Price)
        val igImage: ImageView = view.findViewById(R.id.imageView)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<ImageInfo>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
        )
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = list[position].title
        holder.tvPrice.text = list[position].id
        Glide.with(context).load(list[position].thumbnailUrl).fitCenter().into(holder.igImage)
    }
}