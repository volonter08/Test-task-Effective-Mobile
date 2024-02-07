package com.example.testtaskeffectivemobile.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtaskeffectivemobile.dao.ProductDao
import com.example.testtaskeffectivemobile.data.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject
constructor(private val repository: LoginRepository, private val productDao: ProductDao) :
    ViewModel() {
    val loginLiveData = repository.profileFlow
    fun login(name: String, surname: String, phoneNumber: String) {
        viewModelScope.launch {
            repository.login(name, surname, phoneNumber)
        }
    }

    fun logout() {
        viewModelScope.launch {
            productDao.deleteAllProducts()
            repository.logout()
        }
    }

}