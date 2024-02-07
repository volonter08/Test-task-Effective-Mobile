package com.example.testtaskeffectivemobile.data

import androidx.lifecycle.map
import com.example.testtaskeffectivemobile.dao.ProfileDao
import com.example.testtaskeffectivemobile.entity.Profile
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
@Singleton
class LoginRepository @Inject constructor(val profileDao: ProfileDao) {

    var profileFlow = profileDao.getProfileLiveData().map{
        if (it.isNotEmpty()) {
            it.first()
        }
        else
            null
    }
    suspend fun logout() {
        try {
            profileDao.deleteProfileData()
        }
        catch (e:Exception){

        }
    }

    suspend fun login(name: String, surname: String, phoneNumber: String) {
        // handle login
        try {
            profileDao.setProfileData(Profile(name, surname, phoneNumber))
        } catch (e: Exception) {

        }
    }
}