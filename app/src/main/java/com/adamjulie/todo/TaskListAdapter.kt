package com.adamjulie.todo

import android.app.ActivityManager.TaskDescription
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adamjulie.todo.list.Task

class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    var currentList: List<Task> = emptyList()

    // on utilise `inner` ici afin d'avoir accès aux propriétés de l'adapter directement
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // on utilise `view` ici afin d'avoir accès à la vue de l'item
        private val view = itemView
        private val textView = view.findViewById<TextView>(R.id.task_title)
        private val textViewDescription = view.findViewById<TextView>(R.id.task_description)

        fun bind(taskTitle: String, taskDescription: String) {
            textView.text = taskTitle
            textViewDescription.text = taskDescription
                }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(currentList[position].title, currentList[position].description)
    }
}