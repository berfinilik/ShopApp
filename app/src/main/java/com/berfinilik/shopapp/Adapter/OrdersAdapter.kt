package com.berfinilik.shopapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.berfinilik.shopapp.Model.ItemsModel
import com.berfinilik.shopapp.databinding.ViewholderOrderBinding
import com.bumptech.glide.Glide

class OrdersAdapter(private val orderItems: ArrayList<ItemsModel>) : RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {

    class ViewHolder(val binding: ViewholderOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewholderOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = orderItems[position]

        holder.binding.titleTextView.text = item.title
        holder.binding.priceTextView.text = "₺${item.price}"
        holder.binding.quantityTextView.text = "Adet: ${item.numberInCart}"


        Glide.with(holder.itemView.context)
            .load(item.picUrl[0])
            .into(holder.binding.itemImage)
    }

    override fun getItemCount(): Int {
            return orderItems.size
    }
}
