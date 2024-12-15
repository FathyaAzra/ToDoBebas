package com.example.todobebas.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todobebas.R
import java.text.SimpleDateFormat
import java.util.*

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var todos: List<Todo> = listOf()

    // ViewHolder untuk memegang referensi ke item view
    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val todoName: TextView = itemView.findViewById(R.id.todoName)
        val todoDate: TextView = itemView.findViewById(R.id.todoDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_task_layout, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos[position]
        holder.todoName.text = todo.todo_name
        holder.todoDate.text = formatDate(todo.todo_date)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    // Fungsi untuk memperbarui data RecyclerView
    fun setTodos(newTodos: List<Todo>) {
        todos = newTodos
        notifyDataSetChanged()
    }

    // Fungsi untuk format tanggal dari Long menjadi String
    private fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}