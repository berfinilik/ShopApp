package com.berfinilik.shopapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.berfinilik.shopapp.Model.ItemsModel
import com.berfinilik.shopapp.activity.DetailActivity
import com.berfinilik.shopapp.databinding.ViewholderRecommendedBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions


class PopularAdapter(val items: MutableList<ItemsModel>) :
    RecyclerView.Adapter<PopularAdapter.ViewHolder>() {
    private var context: Context? = null

    class ViewHolder(val binding: ViewholderRecommendedBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularAdapter.ViewHolder {
        context = parent.context
        val binding =
            ViewholderRecommendedBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularAdapter.ViewHolder, position: Int) {
        holder.binding.titleTextView.text = items[position].title
        holder.binding.priceTextView.text = "₺" + items[position].price.toString()
        holder.binding.ratingTextView.text = items[position].rating.toString()

        val requestOptions = RequestOptions().transform(CenterCrop())
        Glide.with(holder.itemView.context)
            .load(items[position].picUrl[0])
            .apply(requestOptions)
            .into(holder.binding.picture)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("object", items[position])
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}