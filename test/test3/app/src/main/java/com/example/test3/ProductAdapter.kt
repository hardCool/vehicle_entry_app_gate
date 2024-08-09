
package com.example.test3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ProductAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val prodName: TextView = itemView.findViewById(R.id.product_name)
        val prodDesc: TextView = itemView.findViewById(R.id.product_desc)
        val prodPrice: TextView = itemView.findViewById(R.id.product_price)
        val prodImage: ImageView = itemView.findViewById(R.id.product_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.prodName.text = product.prod_name
        holder.prodDesc.text = product.prod_desc
        holder.prodPrice.text = product.price

        Glide.with(holder.itemView.context)
            .load(product.prod_image)
            .apply(RequestOptions()
                .placeholder(R.drawable.placeholder) // Placeholder while loading
                .error(R.drawable.error)             // Error image if load fails
            )
            .into(holder.prodImage)
    }

    override fun getItemCount() = products.size
}