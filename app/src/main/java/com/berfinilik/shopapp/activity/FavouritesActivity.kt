package com.berfinilik.shopapp.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.berfinilik.shopapp.Adapter.FavouritesAdapter
import com.berfinilik.shopapp.Model.ItemsModel
import com.berfinilik.shopapp.databinding.ActivityFavoriteBinding


class FavouritesActivity : BaseActivity() {

    private lateinit var favouritesAdapter: FavouritesAdapter
    private val favouriteItems = mutableListOf<ItemsModel>()
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent.getParcelableExtra<ItemsModel>("product")
        product?.let {
            favouritesAdapter.addFavouriteItem(it)
        }

        favouritesAdapter = FavouritesAdapter(this, favouriteItems)
        binding.favouritesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.favouritesRecyclerView.adapter = favouritesAdapter

    }
}