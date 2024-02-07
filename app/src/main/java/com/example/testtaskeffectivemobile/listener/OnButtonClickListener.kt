package com.example.testtaskeffectivemobile.listener

import com.example.testtaskeffectivemobile.data.model.Product

interface OnButtonClickListener {
    fun onProductClick(product: Product, listLinks: List<String>)
    fun onFavoriteAddButtonClick(product: Product)
}