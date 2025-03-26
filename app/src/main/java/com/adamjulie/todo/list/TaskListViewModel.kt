package com.adamjulie.todo.list

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adamjulie.todo.data.Api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TaskListViewModel : ViewModel() {
    private val webService = Api.taskWebService

    public val tasksStateFlow = MutableStateFlow<List<Task>>(emptyList())

    fun refresh() {
        viewModelScope.launch {
            val response = webService.fetchTasks() // Call HTTP (opération longue)
            if (!response.isSuccessful) { // à cette ligne, on a reçu la réponse de l'API
                Log.e("Network", "Error refreshing tasks: ${response.message()}")
                return@launch
            }
            val fetchedTasks = response.body()!!
            tasksStateFlow.value = fetchedTasks // on modifie le flow, ce qui déclenche ses observers
        }
    }

    fun edit(task: Task) {
        viewModelScope.launch {
            val response = webService.update(task = task, id = task.id)
            if (!response.isSuccessful) {
                Log.e("Network", "Error editing task: ${response.raw()}")
                return@launch
            }

            val updatedTask = response.body()!!
            val updatedList = tasksStateFlow.value.map {
                if (it.id == updatedTask.id) updatedTask else it
            }
            tasksStateFlow.value = updatedList
        }
        refresh();
    }



    fun add(task: Task) {
        viewModelScope.launch {
            val response = webService.create(task = task)
            if (!response.isSuccessful) {
                Log.e("Network", "Error adding task: ${response.raw()}")
                return@launch
            }


        }
        refresh();
    }

    fun remove(task: Task) {
        viewModelScope.launch {
            val response = webService.delete(id = task.id)
            if (!response.isSuccessful) {
                Log.e("Network", "Error: ${response.raw()}")
                return@launch
            }

        }
        refresh();
    }

    fun getLastTask() : Task? {
        return tasksStateFlow.value.lastOrNull();
    }

    fun getFirstTask() : Task {
        return tasksStateFlow.value.first()
    }


    fun onLongClickShare(context: Context, task: Task) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Task: ${task.title}")
            putExtra(Intent.EXTRA_TEXT, "Title: ${task.title} \n Description: ${task.description.orEmpty()}")
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share Task"))
    }

}