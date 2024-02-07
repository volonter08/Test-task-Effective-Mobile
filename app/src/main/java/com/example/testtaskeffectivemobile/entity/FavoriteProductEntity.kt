package com.example.testtaskeffectivemobile.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testtaskeffectivemobile.data.model.FeedBack
import com.example.testtaskeffectivemobile.data.model.Info
import com.example.testtaskeffectivemobile.data.model.Price
import com.example.testtaskeffectivemobile.data.model.Product

@Entity
data class FavoriteProductEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val subtitle: String,
    @Embedded
    val price: Price,
    @Embedded
    val feedBack: FeedBack?,
    val tags: List<String>,
    val available: String,
    val description: String,
    val info: List<Info>,
    val ingredients: String,
) {
    constructor(product: Product) : this(
        id = product.id,
        title = product.title,
        subtitle = product.subtitle,
        price = product.price,
        feedBack = product.feedback,
        tags = product.tags,
        available = product.available,
        description = product.description,
        info = product.info,
        ingredients = product.ingredients,
    )
}

fun List<FavoriteProductEntity>.toDto() = map {
    it.run {
        Product(
            id,
            title,
            subtitle,
            price,
            feedBack,
            tags,
            available,
            description,
            info,
            ingredients,
            true
        )
    }
}
fun List<FavoriteProductEntity>.containsId(id:String):Boolean{
    forEach {
        if (it.id ==id)
            return true
    }
    return false
}