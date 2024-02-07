package com.example.testtaskeffectivemobile.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.testtaskeffectivemobile.dao.ProductDao
import com.example.testtaskeffectivemobile.dao.ProfileDao
import com.example.testtaskeffectivemobile.data.model.Info
import com.example.testtaskeffectivemobile.entity.FavoriteProductEntity
import com.example.testtaskeffectivemobile.entity.Profile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Database(entities = [Profile::class,FavoriteProductEntity::class], version = 1, exportSchema = false)
@TypeConverters(ListStringConverter::class,ListInfoConverter::class)
abstract class AppDb : RoomDatabase() {
    abstract fun profileDao():ProfileDao
    abstract fun productDao():ProductDao
}

class ListStringConverter {
    @TypeConverter
    fun  stringToList(value: String): List<String>? {
        return Gson().fromJson(value,  object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun listToString(value: List<String>?): String {
        return if(value == null) "null" else Gson().toJson(value)
    }
}
class ListInfoConverter {
    @TypeConverter
    fun stringToList(value: String): List<Info>? {
        return Gson().fromJson(value,  object : TypeToken<List<Info>>() {}.type)
    }
    @TypeConverter
    fun listToString(value: List<Info>?): String {
        return if(value == null) "null" else Gson().toJson(value)
    }
}