package com.example.todobebas.ui.kalender

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KalenderViewModel : ViewModel() {

    /*private val _tasks = MutableLiveData<Map<String, List<String>>>()

    init {
        // Sample tasks for different dates
        val taskMap = mutableMapOf<String, List<String>>()
        taskMap["01-12-2024"] = listOf("Task 1", "Task 2")
        taskMap["02-12-2024"] = listOf("Task 3", "Task 4")
        taskMap["03-12-2024"] = listOf("Task 5", "Task 6")
        _tasks.value = taskMap
    }

    // Function to get tasks for a specific date
    fun getTasksForDate(date: String): LiveData<List<String>> {
        val taskList = MutableLiveData<List<String>>()
        taskList.value = _tasks.value?.get(date) ?: emptyList()
        return taskList
    } */
}
