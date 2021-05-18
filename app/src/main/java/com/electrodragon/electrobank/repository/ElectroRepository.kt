package com.electrodragon.electrobank.repository

import com.electrodragon.electrobank.model.room_database.dao.UserDao
import com.electrodragon.electrobank.model.room_database.entities.UserEntity
import javax.inject.Inject

class ElectroRepository @Inject constructor(private val userDao: UserDao) {

    fun createNewUser(user: UserEntity) {
        userDao.insertUser(user)
    }

}