package com.example.testtaskeffectivemobile.data

import com.example.testtaskeffectivemobile.api.ProductApiService
import com.example.testtaskeffectivemobile.data.model.Product
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(val productApiService: ProductApiService) {
    val _data: MutableSharedFlow<List<Product>> = MutableSharedFlow()
    val data: Flow<List<Product>>
        get() {
            return _data
        }
    suspend fun loadProducts() {
        val response = productApiService.getProducts()
        _data.emit(response.body()?.items ?: emptyList())
    }
}