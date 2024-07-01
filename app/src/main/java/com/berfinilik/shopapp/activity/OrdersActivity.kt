package com.berfinilik.shopapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.berfinilik.shopapp.Adapter.OrdersAdapter
import com.berfinilik.shopapp.Model.ItemsModel
import com.berfinilik.shopapp.databinding.ActivityOrdersBinding

class OrdersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrdersBinding
    private lateinit var ordersAdapter: OrdersAdapter
    private lateinit var orderItems: ArrayList<ItemsModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        orderItems = intent.getParcelableArrayListExtra<ItemsModel>("orderItems") ?: arrayListOf()

        ordersAdapter = OrdersAdapter(orderItems)
        binding.ordersRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.ordersRecyclerView.adapter = ordersAdapter
    }
}

