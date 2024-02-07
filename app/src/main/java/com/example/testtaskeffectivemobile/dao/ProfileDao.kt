package com.example.testtaskeffectivemobile.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testtaskeffectivemobile.entity.Profile

@Dao
interface ProfileDao {
    @Query("SELECT * FROM Profile")
    fun getProfileLiveData(): LiveData<List<Profile>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setProfileData(profile: Profile)
    @Query("DELETE FROM Profile")
    suspend fun deleteProfileData()
}