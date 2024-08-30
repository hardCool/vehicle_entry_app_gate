package com.example.test1_dash_room

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiController {
    private const val BASE_URL = "http://192.168.1.35/api_nn/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Returns an instance of ApiService to make API calls
    fun getApi(): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
