package com.electrodragon.electrobank.repository

import androidx.lifecycle.LiveData
import com.electrodragon.electrobank.model.room_database.dao.UserDao
import com.electrodragon.electrobank.model.room_database.entities.UserEntity
import javax.inject.Inject

class ElectroRepository @Inject constructor(private val userDao: UserDao) {

    fun createNewUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    fun getUserWithEmail(email: String): LiveData<UserEntity> {
        return userDao.getUserWithEmail(email)
    }

}