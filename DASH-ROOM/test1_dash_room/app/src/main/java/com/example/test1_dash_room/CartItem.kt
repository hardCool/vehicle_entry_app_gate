
package com.example.test1_dash_room.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_cart")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val prod_id: Int,
    val qty: Int,
    val user_id: Int
)
