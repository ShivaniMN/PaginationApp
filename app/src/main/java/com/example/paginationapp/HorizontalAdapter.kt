package com.example.paginationapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paginationapp.models.DataItem
import kotlinx.android.synthetic.main.horizontal_list_item.view.*
import kotlinx.android.synthetic.main.list_item.view.*

class HorizontalAdapter(private val context: Context, private val data: List<DataItem>): RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder>() {

    class HorizontalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(dataItem: DataItem){
            val title: TextView = itemView.hrvTitle
            val description: TextView = itemView.hrvDescription
            val imageView: ImageView = itemView.hrvImageView

            title.text = dataItem.title
            description.text = dataItem.description

            Glide.with(itemView.context).load(dataItem.image).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.horizontal_list_item, parent, false)
        return HorizontalViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size

    }
}