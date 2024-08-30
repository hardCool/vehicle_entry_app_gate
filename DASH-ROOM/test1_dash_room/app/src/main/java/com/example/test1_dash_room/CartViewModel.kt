package com.example.test1_dash_room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.test1_dash_room.data.CartItem

class CartViewModel(private val repository: CartRepository) : ViewModel() {
    fun getAllCartItems(): LiveData<List<CartItem>> = repository.getAllCartItems()

    fun addCartItem(cartItem: CartItem) = viewModelScope.launch {
        repository.insertCartItem(cartItem)
    }
}
