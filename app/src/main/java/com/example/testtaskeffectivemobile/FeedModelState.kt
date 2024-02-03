package com.example.testtaskeffectivemobile


data class FeedModelState (
    val loading: Boolean = false,
    val isRefreshing:Boolean = false,
    val isSaved:Boolean  = false,
    val isLogin:Boolean = false,
    val isLogout:Boolean = false,
    val error: Error?=null
)