package com.example.testtaskeffectivemobile.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testtaskeffectivemobile.data.model.FeedBack
import com.example.testtaskeffectivemobile.data.model.Info
import com.example.testtaskeffectivemobile.data.model.Price

@Entity
data class ProductEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val subtitle: String,
    @Embedded
    val price: Price,
    @Embedded
    val feedBack: FeedBack?,
    val tag: List<String>,
    val available: String,
    val description: String,
    val info: List<Info>,
    val ingredients: String
)