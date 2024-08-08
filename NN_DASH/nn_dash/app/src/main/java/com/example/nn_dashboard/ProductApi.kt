package com.example.nn_dashboard


import retrofit2.Call
import retrofit2.http.GET

interface ProductApi {
    @GET("fetch_products.php")
    fun getProducts(): Call<List<Product>>
}
