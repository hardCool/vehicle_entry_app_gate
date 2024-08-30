package com.example.test1_dash_room.data

import com.example.test1_dash_room.Product
import com.example.test1_dash_room.data.local.ProductDao

class ProductRepository(private val productDao: ProductDao) {

    // Method to fetch products from the database
    suspend fun getProducts(): List<Product> {
        return productDao.getAllProducts()
    }
}
