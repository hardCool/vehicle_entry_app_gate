package com.example.test3

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("fetch_products.php")
    fun getProducts(): Call<List<Product>>
}
