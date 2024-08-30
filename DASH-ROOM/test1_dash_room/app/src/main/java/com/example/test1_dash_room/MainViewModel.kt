package com.example.test1_dash_room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    init {
        // Initialize or fetch data here
        fetchProducts()
    }

    private fun fetchProducts() {
        // Simulate fetching products from a repository or database
        viewModelScope.launch {
            // Replace this with your actual data fetching logic
            _products.value = listOf(
                Product(id = 1, name = "Product 1"),
                Product(id = 2, name = "Product 2")
            )
        }
    }

    fun getProducts(): LiveData<List<Product>> = products
}
