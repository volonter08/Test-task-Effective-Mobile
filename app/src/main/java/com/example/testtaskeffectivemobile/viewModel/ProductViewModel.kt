package com.example.testtaskeffectivemobile.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.testtaskeffectivemobile.TypeOfSort
import com.example.testtaskeffectivemobile.data.ProductRepository
import com.example.testtaskeffectivemobile.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {
    val data = repository.data.map{ products ->
        when(typeOfSort.value){
            TypeOfSort.SORT_BY_POPULATE -> products.sortedByDescending {
                it.feedback?.rating
            }
            TypeOfSort.SORT_BY_INCREASING_PRICE -> products.sortedBy {
                it.price.priceWithDiscount.toDouble()
            }
            TypeOfSort.SORT_BY_REDUCING_PRICE -> products.sortedByDescending {
                it.price.priceWithDiscount.toDouble()
            }
            null -> products
        }
    }.asLiveData()
    val dataFavoriteProducts = repository.dataFavoriteProducts.asLiveData()
    private val _selectedTag: MutableLiveData<String> = MutableLiveData("watch_all")

    val typeOfSort: MutableLiveData<TypeOfSort> = MutableLiveData(TypeOfSort.SORT_BY_POPULATE)
    val selectedTag: LiveData<String>
        get() = _selectedTag
    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            repository.loadProducts()
            repository.initAddingToFavoriteListener()
        }
    }

    fun selectTag(tag: String) {
        _selectedTag.value = tag

    }

    fun unselectTag() {
        _selectedTag.value = "watch_all"
    }

    fun addToFavorite(product: Product) {
        viewModelScope.launch {
            repository.addToFavorite(product)
        }
    }
    fun sortProductByPopulate() {
        viewModelScope.launch {
            repository.sortProductByPopulate()
        }
    }

    fun sortProductByIncreasingPrice() {
        viewModelScope.launch {
            repository.sortProductByIncreasingPrice()
        }
    }

    fun sortProductByReducingPrice() {
        viewModelScope.launch{
            repository.sortProductByReducingPrice()
        }
    }
}