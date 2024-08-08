package com.example.nn_dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ProductRepository(application)
    private val _allProducts = MutableLiveData<List<Product>>()
    val allProducts: LiveData<List<Product>> get() = _allProducts

    init {
        fetchProductsFromServer()
    }

    private fun fetchProductsFromServer() {
        viewModelScope.launch {
            val products = repository.fetchProductsFromServer()
            _allProducts.postValue(products)
        }
    }
}
