package com.adamjulie.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adamjulie.todo.list.Task


object MyItemsDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) : Boolean {
        return oldItem.id == newItem.id// comparaison: est-ce la même "entité" ? => même id?
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task) : Boolean {
        return (oldItem.title == newItem.title && oldItem.description == newItem.description)// comparaison: est-ce le même "contenu" ? => mêmes valeurs? (avec data class: simple égalité)
    }
}


class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(MyItemsDiffCallback) {

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


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(currentList[position].title, currentList[position].description)
    }

    //Dans l'adapter, ajouter une propriété onClickDelete
    var onClickDelete: (Task) -> Unit = {}
}