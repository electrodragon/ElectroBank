package com.electrodragon.electrobank.di

import android.app.Application
import androidx.room.Room
import com.electrodragon.electrobank.model.room_database.dao.UserDao
import com.electrodragon.electrobank.model.room_database.db.ElectroDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDatabaseModule {
    @Provides
    @Singleton
    fun provideElectroDB(application: Application): ElectroDB {
        return Room.databaseBuilder(application, ElectroDB::class.java, "electro_bank_db").build()
    }

    @Provides
    @Singleton
    fun provideUserDao(electroDB: ElectroDB): UserDao {
        return electroDB.userDao()
    }
}