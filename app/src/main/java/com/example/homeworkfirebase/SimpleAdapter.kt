package com.example.homeworkfirebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SimpleAdapter(
    private val items: List<Product>,
    private val onClick: (Product) -> Unit
) : RecyclerView.Adapter<SimpleAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val imgProduct: ImageView = view.findViewById(R.id.imgProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]

        holder.tvName.text = item.name
        holder.tvPrice.text = "$${item.price}"
        holder.tvDescription.text = item.description

        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .into(holder.imgProduct)

        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }


    override fun getItemCount(): Int = items.size
}
