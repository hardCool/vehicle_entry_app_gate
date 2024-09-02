package com.example.test1_dash_room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test1_dash_room.data.local.AppDatabase
import com.example.test1_dash_room.data.ProductRepository
import com.example.test1_dash_room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ProductViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Properly inflate the binding class
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the ProductDao and Repository
        val productDao = AppDatabase.getDatabase(application).productDao()
        val repository = ProductRepository(productDao)
        val viewModelFactory = ProductViewModelFactory(repository)

        // Initialize ViewModel using the ViewModelFactory
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProductViewModel::class.java)

        // Set up RecyclerView with adapter
        adapter = ProductAdapter()
        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewProducts.adapter = adapter

        // Observe LiveData from ViewModel
        viewModel.products.observe(this) { products ->
            adapter.submitList(products)
        }
    }
}
