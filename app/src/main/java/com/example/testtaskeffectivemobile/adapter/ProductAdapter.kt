package com.example.testtaskeffectivemobile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.testtaskeffectivemobile.data.model.Product
import com.example.testtaskeffectivemobile.databinding.ItemProductBinding
import com.example.testtaskeffectivemobile.diffUtil.ProductDiffUtilCallback
import com.example.testtaskeffectivemobile.listener.OnButtonClickListener
import com.example.testtaskeffectivemobile.viewHolder.ProductViewHolder

class ProductAdapter(private val listener:OnButtonClickListener) : ListAdapter<Product, ProductViewHolder>(ProductDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(parent.context, binding,listener)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun sortProductByPopulate() {
        submitList(currentList.sortedByDescending {
            it.feedback?.rating
        })
    }

    fun sortProductByIncreasingPrice() {

        submitList(currentList.sortedBy {
            it.price.priceWithDiscount.toInt()
        })

    }

    fun sortProductByReducingPrice() {

        submitList(currentList.sortedByDescending {
            it.price.priceWithDiscount.toInt()
        })

    }
}