package com.example.testtaskeffectivemobile.data.model

import android.content.Context
import com.example.testtaskeffectivemobile.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Items(
    val items: List<Product>
) {
    companion object {
        fun getFromJson(context: Context): Items =
            Gson().fromJson(
                context.resources.openRawResource(R.raw.items).reader(),
                Items::class.java
            )
    }
}