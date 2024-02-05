package com.example.testtaskeffectivemobile.data.model

data class Product(
    val id:String,
    val title:String,
    val subtitle:String,
    val price: Price,
    val feedback: FeedBack?,
    val tags:List<String>,
    val available:String,
    val description:String,
    val info :List<Info>,
    val ingredients:String
)