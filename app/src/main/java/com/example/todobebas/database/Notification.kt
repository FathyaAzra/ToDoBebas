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
    indices = [Index(value = ["todo_id"])]
)
data class Notification(
    @PrimaryKey(autoGenerate = true) val notif_id: Int,
    val todo_id: Int,
    val notif_type: String,
    val notif_time: Long,
    val is_active: Boolean = true
)

