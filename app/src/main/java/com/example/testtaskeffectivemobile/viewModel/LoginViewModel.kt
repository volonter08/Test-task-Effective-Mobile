package com.example.testtaskeffectivemobile.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.testtaskeffectivemobile.data.LoginRepository
import com.example.testtaskeffectivemobile.entity.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject
constructor( val repository: LoginRepository) : ViewModel() {
    val loginLiveData = repository.profileFlow
    fun login(name: String, surname: String, phoneNumber: String) {
        viewModelScope.launch {
            repository.login(name, surname, phoneNumber)
        }
    }
    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }
}