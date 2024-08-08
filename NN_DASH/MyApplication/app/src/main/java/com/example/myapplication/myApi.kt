package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET

interface MyApi {
    @GET("fetch_product.php")
    fun getModels(): Call<List<Model>>
}
