package com.berfinilik.shopapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.berfinilik.shopapp.Adapter.BrandAdapter
import com.berfinilik.shopapp.Adapter.PopularAdapter
import com.berfinilik.shopapp.Model.BrandModel
import com.berfinilik.shopapp.Model.SliderModel
import com.berfinilik.shopapp.Adapter.SliderAdapter
import com.berfinilik.shopapp.ViewModel.MainViewModel
import com.berfinilik.shopapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val currentUser = auth.currentUser
        val email = currentUser?.email

        initBanner()
        initBrand()
        initPopular()
        initBottomMenu()

        binding.chatbotIcon.setOnClickListener {
            startActivity(
                Intent(
                    this,ChatBotActivity::class.java
                )
            )
        }
    }

    private fun initBottomMenu() {
        binding.cartSepet.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    CartActivity::class.java
                )
            )
        }
        binding.cartAnasayfa.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }
        binding.cartFavoriler.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    FavouritesActivity::class.java
                )
            )
        }
        binding.cartProfil.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    UserInfoActivity::class.java
                )
            )
        }

    }

    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModel.banners.observe(this, Observer { items ->
            banners(items)
            binding.progressBarBanner.visibility = View.GONE
        })
        viewModel.loadBanners()
    }

    private fun banners(images: List<SliderModel>) {
        binding.viewpagerSlider.adapter = SliderAdapter(images, binding.viewpagerSlider)
        binding.viewpagerSlider.clipToPadding = false
        binding.viewpagerSlider.clipChildren = false
        binding.viewpagerSlider.offscreenPageLimit = 3
        binding.viewpagerSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }
        binding.viewpagerSlider.setPageTransformer(compositePageTransformer)
        if (images.size > 1) {
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.attachTo(binding.viewpagerSlider)
        }
    }

    private fun initBrand() {
        binding.progressBarBrand.visibility = View.VISIBLE
        viewModel.brands.observe(this, Observer { brands ->
            binding.viewBrand.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.viewBrand.adapter = BrandAdapter(brands.toMutableList()) { brand ->
                loadPopularByBrand(brand)
            }
            binding.progressBarBrand.visibility = View.GONE
        })
        viewModel.loadBrand()
    }

    private fun loadPopularByBrand(brand: BrandModel) {
        binding.progressBarPopular.visibility = View.VISIBLE
        viewModel.getPopularByBrand(brand.id.toLong()).observe(this, Observer { products ->
            binding.viewPopular.adapter = PopularAdapter(products)
            binding.progressBarPopular.visibility = View.GONE
        })
    }

    private fun initPopular() {
        binding.progressBarPopular.visibility = View.VISIBLE
        viewModel.popular.observe(this, Observer {
            binding.viewPopular.layoutManager = GridLayoutManager(this@MainActivity, 2)
            binding.viewPopular.adapter = PopularAdapter(it)
            binding.progressBarPopular.visibility = View.GONE
        })
        viewModel.loadPopular()
    }
}