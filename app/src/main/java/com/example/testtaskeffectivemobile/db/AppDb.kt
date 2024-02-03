package com.example.testtaskeffectivemobile.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testtaskeffectivemobile.dao.ProfileDao
import com.example.testtaskeffectivemobile.entity.Profile


@Database(entities = [Profile::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun profileDao():ProfileDao
}