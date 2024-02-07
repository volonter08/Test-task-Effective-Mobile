package com.example.testtaskeffectivemobile.data

import android.content.Context
import com.example.testtaskeffectivemobile.TypeOfSort
import com.example.testtaskeffectivemobile.api.ProductApiService
import com.example.testtaskeffectivemobile.dao.ProductDao
import com.example.testtaskeffectivemobile.data.model.Items
import com.example.testtaskeffectivemobile.data.model.Product
import com.example.testtaskeffectivemobile.entity.FavoriteProductEntity
import com.example.testtaskeffectivemobile.entity.containsId
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val productApiService: ProductApiService,
    private val productDao: ProductDao
) {
    private val _data: MutableStateFlow<List<Product>> = MutableStateFlow(emptyList())
    val data: StateFlow<List<Product>>
        get() {
            return _data
        }
    val dataFavoriteProducts = productDao.getProductFlow()


    suspend fun loadProducts() {
        _data.value = (try {
            productApiService.getProducts().body()?.items ?: emptyList()
        } catch (e: Exception) {
            Items.getFromJson(context).items
        })
    }

    suspend fun initAddingToFavoriteListener() {
        dataFavoriteProducts.collect {
            _data.value = _data.value.map { product ->
                if (it.containsId(product.id))
                    product.copy(isFavorite = true)
                else
                    product.copy(isFavorite = false)
            }
        }
    }
    fun sortProductByPopulate() {
        _data.value= _data.value.sortedByDescending {
            it.feedback?.rating
        }
    }

    fun sortProductByIncreasingPrice() {
        _data.value= _data.value.sortedBy {
            it.price.priceWithDiscount.toInt()
        }
    }

    fun sortProductByReducingPrice() {
        _data.value= _data.value.sortedByDescending {
            it.price.priceWithDiscount.toInt()
        }
    }

    suspend fun addToFavorite(product: Product) {
        if (!product.isFavorite)
            productDao.insertProduct(product = FavoriteProductEntity(product))
        else
            productDao.deleteProductById(product.id)
    }
}