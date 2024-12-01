package com.example.todobebas.notif

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notifications")
    fun getAll():List<Notification>

    @Query("SELECT * FROM notifications WHERE todo_id = :todoId")
    fun getNotificationsForTodo(todoId: Int): List<Notification>


    @Insert
    fun insertAll(vararg transaction: Notification)

    @Delete
    fun delete(transaction: Notification)

    @Update
    fun update(vararg transaction: Notification)

    @Query("UPDATE notifications SET is_active = :active WHERE notif_id = :notifId")
    fun updateNotificationStatus(notifId: Int, active: Boolean)


}
