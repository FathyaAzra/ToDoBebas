package com.example.todobebas.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotificationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: Notification)

    @Query("SELECT * FROM notifications WHERE todo_id = :todoId")
    suspend fun getNotificationsForTodo(todoId: Int): List<Notification>

    @Delete
    suspend fun deleteNotification(notification: Notification)

    @Query("DELETE FROM notifications WHERE todo_id = :todoId")
    suspend fun deleteNotificationsForTodo(todoId: Int)

    @Update
    suspend fun updateNotification(notification: Notification)
}
