package com.berfinilik.shopapp.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.berfinilik.shopapp.Adapter.CartAdapter
import com.berfinilik.shopapp.Helper.ManagmentCart
import com.berfinilik.shopapp.databinding.ActivityCartBinding
import com.berfinilik.shopapp.Helper.ChangeNumberItemsListener
import com.google.android.material.snackbar.Snackbar

class CartActivity : BaseActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var managmentCart: ManagmentCart
    private lateinit var cartAdapter:CartAdapter
    private var tax: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        cartAdapter=CartAdapter(managmentCart.getListCart(),this,object:
        ChangeNumberItemsListener{
            override fun onChanged() {
                calculateCart()
            }
        })

        binding.buttonOnayla.setOnClickListener {
            Snackbar.make(binding.main, "Sepet onaylandı", Snackbar.LENGTH_SHORT).show()
        }


        setVariable()
        initCartList()
        calculateCart()
    }

    private fun initCartList() {
        binding.viewCart.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.viewCart.adapter =cartAdapter


        with(binding) {
            emptyTxt.visibility =
                if (managmentCart.getListCart().isEmpty()) View.VISIBLE else View.GONE
            scrollView2.visibility =
                if (managmentCart.getListCart().isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun calculateCart() {
        val percentTax = 0.02
        val delivery = 10.0
        tax = Math.round((managmentCart.getTotalFee() * percentTax) * 100) / 100.0
        val total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) / 100
        val itemTotal = Math.round(managmentCart.getTotalFee() * 100) / 100

        with(binding) {
            totalFeeTxt.text = "₺$itemTotal"
            taxTxt.text = "₺$tax"
            deliveryTxt.text = "₺$delivery"
            totalTxt.text = "₺$total"
        }
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener { finish() }
    }

}