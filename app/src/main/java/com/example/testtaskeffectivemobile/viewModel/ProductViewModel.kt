package com.example.testtaskeffectivemobile.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.testtaskeffectivemobile.data.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor (private val repository: ProductRepository): ViewModel() {
    val data = repository.data.asLiveData()
    val selectedTags: MutableLiveData<Set<String>> = MutableLiveData()
    init {
        loadProducts()
    }
    fun loadProducts(){
       viewModelScope.launch {
           repository.loadProducts()
       }
    }
    fun selectTag(tag:String){
        selectedTags.value = (selectedTags.value?:emptySet()) + tag

    }
    fun unselectTag(tag: String){
        selectedTags.value = (selectedTags.value)?.minus(tag)
    }

    override fun onCleared() {
        println("T")
        super.onCleared()

    }
}