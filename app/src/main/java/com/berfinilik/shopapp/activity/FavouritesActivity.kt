package com.berfinilik.shopapp.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.berfinilik.shopapp.Adapter.FavouritesAdapter
import com.berfinilik.shopapp.ViewModel.MainViewModel
import com.berfinilik.shopapp.databinding.ActivityFavoriteBinding

class FavouritesActivity : BaseActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: FavouritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        adapter = FavouritesAdapter(this, mutableListOf())
        binding.favouritesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.favouritesRecyclerView.adapter = adapter

        viewModel.loadFavourites()
        viewModel.favourites.observe(this, Observer { items ->
            adapter.addFavouriteItems(items)
        })
    }
}
