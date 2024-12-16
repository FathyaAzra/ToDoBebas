package com.example.todobebas.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import androidx.recyclerview.widget.RecyclerView
import com.example.todobebas.R
import java.text.SimpleDateFormat
import java.util.*

class TodoAdapter(private val onItemClick: (Todo) -> Unit) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private val todos = mutableListOf<Todo>()

    // Update the list of todos in the adapter
    fun setTodos(newTodos: List<Todo>) {
        todos.clear()
        todos.addAll(newTodos)
        notifyDataSetChanged()
    }

    // ViewHolder class
    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleText: TextView = itemView.findViewById(R.id.titleText)
        private val dateText: TextView = itemView.findViewById(R.id.dateText)
        private val icon: ImageView = itemView.findViewById(R.id.icon)
        private val cardView: MaterialCardView = itemView.findViewById(R.id.cardView) // Corrected to MaterialCardView

        // Bind data to the view
        fun bind(todo: Todo, onItemClick: (Todo) -> Unit) {
            titleText.text = todo.todo_name
            dateText.text = formatDate(todo.todo_date)

            // Set the icon
            icon.setImageResource(R.drawable.triangle_24px)

            // Set background color based on the task's status
            when (todo.todo_status) {
                "not yet" -> cardView.setCardBackgroundColor(itemView.context.getColor(R.color.darkblue))
                "on going" -> cardView.setCardBackgroundColor(itemView.context.getColor(R.color.magenta))
                "done" -> cardView.setCardBackgroundColor(itemView.context.getColor(R.color.lightblue))
            }

            // Handle the click to toggle the task's status
            itemView.setOnClickListener {
                val updatedTodo = toggleStatus(todo)
                onItemClick(updatedTodo)  // Notify the activity/fragment to update the database
            }
        }

        // Toggle status of the task
        private fun toggleStatus(todo: Todo): Todo {
            val newStatus = when (todo.todo_status) {
                "not yet" -> "on going"
                "on going" -> "done"
                "done" -> "not yet"
                else -> "not yet"
            }
            return todo.copy(todo_status = newStatus)  // Return a new Todo object with updated status
        }

        // Format the date into a readable format
        private fun formatDate(timestamp: Long): String {
            val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            return formatter.format(Date(timestamp))
        }
    }

    // Create a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_task_layout, parent, false)
        return TodoViewHolder(view)
    }

    // Bind data to the ViewHolder
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todos[position], onItemClick)  // Pass onItemClick here
    }

    // Get the number of items in the list
    override fun getItemCount(): Int = todos.size
}
