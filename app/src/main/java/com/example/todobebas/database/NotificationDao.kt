package com.example.todobebas.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notifications")
    fun getAll():List<Notification>

    @Insert
    fun insertAll(vararg transaction: Notification)

    @Delete
    fun delete(transaction: Notification)

    @Update
    fun update(vararg transaction: Notification)
}
