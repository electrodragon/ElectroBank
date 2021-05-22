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

    fun getUserWithEmailOrAccountNumber(email: String, accountNumber: String): UserEntity {
        return userDao.getUserWithEmailOrAccountNumber(email, accountNumber)
    }

    fun getUserWithId(id: Int): LiveData<UserEntity> {
        return userDao.getUserWithId(id)
    }

    fun setAmount(amount: Double, id: Int) {
        userDao.setAmount(amount, id)
    }
}