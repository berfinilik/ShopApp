package com.berfinilik.shopapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.berfinilik.shopapp.Model.ItemsModel
import com.berfinilik.shopapp.databinding.ItemFavoriteBinding

class FavouritesAdapter(private val context: Context, private var favouriteItems: MutableList<ItemsModel>) : RecyclerView.Adapter<FavouritesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFavoriteBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = favouriteItems[position]
    }

    override fun getItemCount(): Int {
        return favouriteItems.size
    }

    fun addFavouriteItem(item: ItemsModel) {
        favouriteItems.add(item)
        notifyDataSetChanged()
    }
}