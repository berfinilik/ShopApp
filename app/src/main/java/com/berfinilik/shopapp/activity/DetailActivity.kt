package com.berfinilik.shopapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.berfinilik.shopapp.Adapter.ColorAdapter
import com.berfinilik.shopapp.Adapter.FavouritesAdapter
import com.berfinilik.shopapp.Adapter.SizeAdapter
import com.berfinilik.shopapp.Adapter.SliderAdapter
import com.berfinilik.shopapp.Helper.ManagmentCart
import com.berfinilik.shopapp.Model.ItemsModel
import com.berfinilik.shopapp.Model.SliderModel
import com.berfinilik.shopapp.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel
    private var numberOder = 1
    private lateinit var managmentCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.favBtn.setOnClickListener {
            addToFavourites()
            Toast.makeText(this,"Favorilere eklendi",Toast.LENGTH_SHORT).show()
        }

        managmentCart = ManagmentCart(this)

        getBundle()
        banners()
        initLists()
    }
    private fun addToFavourites(){
        val product = intent.getParcelableExtra<ItemsModel>("product")
        val intent = Intent(this, FavouritesActivity::class.java)
        intent.putExtra("product", product)
    }

    private fun initLists() {
        val sizeList = ArrayList<String>()
        for (size in item.size) {
            sizeList.add(size.toString())
        }

        binding.sizeList.adapter = SizeAdapter(sizeList)
        binding.sizeList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val colorList = ArrayList<String>()
        for (imageUrl in item.picUrl) {
            colorList.add(imageUrl)
        }

        binding.colorList.adapter = ColorAdapter(colorList)
        binding.colorList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun banners() {
        val sliderItems = ArrayList<SliderModel>()
        for (imageUrl in item.picUrl) {
            sliderItems.add(SliderModel(imageUrl))
        }

        binding.slider.adapter = SliderAdapter(sliderItems, binding.slider)
        binding.slider.clipToPadding = true
        binding.slider.clipChildren = true
        binding.slider.offscreenPageLimit = 1


        if (sliderItems.size > 1) {
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.attachTo(binding.slider)
        }
    }

    private fun getBundle() {
        item = intent.getParcelableExtra("object")!!

        binding.titleTextView.text = item.title
        binding.descriptionTxt.text = item.description
        binding.priceTextView.text = "₺" + item.price
        binding.ratingTextView.text = "${item.rating} "
        binding.addToCartBtn.setOnClickListener {
            item.numberInCart = numberOder
            managmentCart.insertFood(item)
        }
        binding.backBtn.setOnClickListener { finish() }
        binding.addToCartBtn.setOnClickListener {
            Toast.makeText(this,"Sepete eklendi",Toast.LENGTH_SHORT).show()
        }
        binding.cartSepet.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }
}