package com.electrodragon.electrobank.model.room_database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.electrodragon.electrobank.model.room_database.entities.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: UserEntity)

    @Query("SELECT * FROM bank_user WHERE email=:email")
    fun getUserWithEmail(email: String): LiveData<UserEntity>

    @Query("SELECT * FROM bank_user WHERE email=:email or account_number=:accountNumber")
    fun getUserWithEmailOrAccountNumber(email: String, accountNumber: String): UserEntity

    @Query("SELECT * FROM bank_user WHERE id=:id")
    fun getUserWithId(id: Int): LiveData<UserEntity>
}