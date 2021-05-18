package com.electrodragon.electrobank.model.room_database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.electrodragon.electrobank.model.room_database.dao.UserDao
import com.electrodragon.electrobank.model.room_database.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class ElectroDB: RoomDatabase() {
    abstract fun userDao(): UserDao
}