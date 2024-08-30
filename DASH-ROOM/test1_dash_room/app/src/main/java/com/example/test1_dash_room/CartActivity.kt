package com.example.test1_dash_room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test1_dash_room.data.local.AppDatabase
import com.example.test1_dash_room.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var viewModel: CartViewModel
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Properly inflate the binding class
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize repository and factory
        val cartDao = AppDatabase.getDatabase(application).cartDao()
        val repository = CartRepository(cartDao)
        val viewModelFactory = CartViewModelFactory(repository)

        // Initialize ViewModel using the ViewModelFactory
        viewModel = ViewModelProvider(this, viewModelFactory).get(CartViewModel::class.java)

        // Set up RecyclerView with adapter
        adapter = CartAdapter()
        binding.recyclerViewCart.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewCart.adapter = adapter

        // Observe LiveData from ViewModel
        viewModel.getAllCartItems().observe(this) { cartItems ->
            adapter.submitList(cartItems)
        }
    }
}
