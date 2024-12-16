package com.example.todobebas.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "catatan",
    foreignKeys = [
        ForeignKey(
            entity = Todo::class,
            parentColumns = ["todo_id"],
            childColumns = ["task_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Catatan(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val task_id: Int,
    val catatan: String
)
