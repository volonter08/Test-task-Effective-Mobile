package com.example.testtaskeffectivemobile.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.testtaskeffectivemobile.data.model.Product

class ProductDiffUtilCallback: DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem.id==newItem.id

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean=
        oldItem==newItem
}