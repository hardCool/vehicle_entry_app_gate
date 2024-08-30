package com.example.test1_dash_room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_products")
data class Product(
    @PrimaryKey(autoGenerate = true) val prod_id: Int = 0,
    val cat_id: Int,
    val prod_name: String,
    val prod_image: String,
    val prod_desc: String,
    val price: Int,
    val prod_status: Double
)