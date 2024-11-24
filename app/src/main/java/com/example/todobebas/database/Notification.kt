package com.example.todobebas.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "notifications",
    foreignKeys = [ForeignKey(
        entity = Todo::class,
        parentColumns = ["todo_id"],
        childColumns = ["todo_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["todo_id"])] // Index for faster lookups
)
data class Notification(
    @PrimaryKey(autoGenerate = true) val notif_id: Int,
    val todo_id: Int, // Foreign key
    val notif_type: String, // Notification type (e.g., "7d", "3d", "1h")
    val notif_time: Long, // Timestamp for when the notification should trigger
    val is_active: Boolean = true // Whether the notification is enabled
)

