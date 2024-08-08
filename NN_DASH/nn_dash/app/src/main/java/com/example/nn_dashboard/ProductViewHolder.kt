package com.example.nn_dashboard

import androidx.recyclerview.widget.RecyclerView
import com.example.nn_dashboard.databinding.ItemProductBinding

class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.productName.text = product.name
        binding.productPrice.text = product.price.toString()
    }
}
