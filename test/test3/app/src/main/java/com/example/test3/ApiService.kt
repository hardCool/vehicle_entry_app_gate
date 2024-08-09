package com.example.test3

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("fetch_products.php") // Ensure the endpoint is correct
    fun getProducts(): Call<List<Product>>
}
