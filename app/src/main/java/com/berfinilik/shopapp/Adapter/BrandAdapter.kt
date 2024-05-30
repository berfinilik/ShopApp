package com.berfinilik.shopapp.Adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.berfinilik.shopapp.Model.BrandModel
import com.berfinilik.shopapp.R
import com.berfinilik.shopapp.databinding.ViewholderBrandBinding
import com.bumptech.glide.Glide

class BrandAdapter(val items: MutableList<BrandModel>) :
    RecyclerView.Adapter<BrandAdapter.Viewholder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1
    private lateinit var context: Context

    inner class Viewholder(val binding: ViewholderBrandBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandAdapter.Viewholder {
        context = parent.context
        val binding = ViewholderBrandBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: BrandAdapter.Viewholder, position: Int) {
        val item = items[position]
        holder.binding.titleTv.text = item.title

        Glide.with(holder.itemView.context)
            .load(item.picUrl)
            .into(holder.binding.picture)

        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }
        holder.binding.titleTv.setTextColor(context.resources.getColor(R.color.white))
        if (selectedPosition == position) {
            holder.binding.picture.setBackgroundResource(0)
            ImageViewCompat.setImageTintList(
                holder.binding.picture,
                ColorStateList.valueOf(context.getColor(R.color.white))
            )

            holder.binding.titleTv.visibility = View.VISIBLE
        } else {
            holder.binding.picture.setBackgroundResource(R.drawable.grey_bg)
            holder.binding.mainLayout.setBackgroundResource(0)
            ImageViewCompat.setImageTintList(
                holder.binding.picture,
                ColorStateList.valueOf(context.getColor(R.color.black))
            )

            holder.binding.titleTv.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = items.size
}