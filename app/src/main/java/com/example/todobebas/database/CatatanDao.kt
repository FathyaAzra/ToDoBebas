package com.example.todobebas.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update

@Dao
interface CatatanDao {

    @Query("SELECT * FROM catatan")
    fun getAll(): List<Catatan>  // Fixed query to select all records

    @Insert
    fun insert(catatan: Catatan)

    @Delete
    fun delete(catatan: Catatan)  // Use Catatan instead of Todo

    @Update
    fun update(vararg catatan: Catatan)  // Use Catatan instead of Todo
}
