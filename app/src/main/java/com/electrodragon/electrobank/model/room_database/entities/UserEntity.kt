package com.electrodragon.electrobank.model.room_database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bank_user")
class UserEntity(
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    val email: String,
    val password: String,
    @ColumnInfo(name = "account_number") val accountNumber: String,
) {
    @PrimaryKey(autoGenerate = true)  var id: Int = 0
    var amount: Double = 0.0
}
