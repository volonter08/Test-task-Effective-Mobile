package com.example.testtaskeffectivemobile.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profile(
    @PrimaryKey val name: String = "",
    val surname: String = "",
    val phoneNumber: String = ""
)
