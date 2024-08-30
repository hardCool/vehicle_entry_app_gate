package com.example.test1_dash_room.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.test1_dash_room.data.CartItem

@Dao
interface CartDao {
    @Query("SELECT * FROM tbl_cart")
    fun getAllCartItems(): LiveData<List<CartItem>>

    @Insert
    suspend fun insertCartItem(cartItem: CartItem)
}
