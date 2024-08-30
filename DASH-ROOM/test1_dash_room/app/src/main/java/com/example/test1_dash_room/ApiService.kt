package com.example.test1_dash_room

import retrofit2.http.GET

interface ApiService {
    @GET("getProducts")
    suspend fun getProducts(): List<Product>
}
