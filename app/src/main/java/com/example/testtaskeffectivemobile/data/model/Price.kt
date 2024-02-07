package com.example.testtaskeffectivemobile.data.model

import java.io.Serializable

data class Price(
    val price:String,
    val discount: Int,
    val priceWithDiscount:String,
    val unit:String
):Serializable