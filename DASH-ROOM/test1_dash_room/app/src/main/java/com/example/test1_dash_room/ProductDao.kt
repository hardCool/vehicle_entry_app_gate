package com.example.test1_dash_room.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.test1_dash_room.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM tbl_products")
    suspend fun getAllProducts(): List<Product>  // Changed return type to List<Product>

    @Insert
    suspend fun insertProduct(product: Product)
}
