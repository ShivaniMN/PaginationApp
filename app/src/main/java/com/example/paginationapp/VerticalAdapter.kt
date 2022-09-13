package com.example.paginationapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paginationapp.models.DataItem
import kotlinx.android.synthetic.main.list_item.view.*

class VerticalAdapter(private val context: Context, private val data: List<DataItem>):RecyclerView.Adapter<VerticalAdapter.VerticalViewHolder>() {
    class VerticalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(dataItem: DataItem){
            val title: TextView = itemView.title
            val description: TextView = itemView.description
            val imageView: ImageView = itemView.imageView

            title.text = dataItem.title
            description.text = dataItem.description

            Glide.with(itemView.context).load(dataItem.image).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalAdapter.VerticalViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return VerticalAdapter.VerticalViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VerticalAdapter.VerticalViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }


}