package com.example.test1_dash_room

import androidx.lifecycle.LiveData
import com.example.test1_dash_room.data.local.CartDao
import com.example.test1_dash_room.data.CartItem

class CartRepository(private val cartDao: CartDao) {
    fun getAllCartItems(): LiveData<List<CartItem>> = cartDao.getAllCartItems()

    suspend fun insertCartItem(cartItem: CartItem) {
        cartDao.insertCartItem(cartItem)
    }
}
