package com.example.todobebas.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val todo_id: Int,
    val todo_name: String,
    val todo_desc: String,
    val todo_date: Long,
) : Serializable {
}
