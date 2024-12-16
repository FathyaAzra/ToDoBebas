package com.example.todobebas.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {

    @Query("SELECT * FROM todos")
    fun getAll(): List<Todo>

    @Query("SELECT * FROM todos WHERE todo_date >= :startOfDay AND todo_date <= :endOfDay")
    fun getTasksForDate(startOfDay: Long, endOfDay: Long): List<Todo>

    @Insert
    fun insertAll(vararg todo: Todo)

    @Delete
    fun delete(transaction: Todo)

    @Update
    fun update(vararg transaction: Todo)
}