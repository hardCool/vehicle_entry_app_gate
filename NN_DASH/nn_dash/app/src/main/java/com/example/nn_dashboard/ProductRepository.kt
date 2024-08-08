package com.example.nn_dashboard

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository(application: Application) {

    private val productApi = RetrofitClient.instance.create(ProductApi::class.java)
    private val _allProducts = MutableLiveData<List<Product>>()
    val allProducts: LiveData<List<Product>> get() = _allProducts

    fun fetchProductsFromServer() {
        productApi.getProducts().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    _allProducts.value = response.body()
                } else {
                    // Handle the error response
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                // Handle the failure
            }
        })
    }
}
