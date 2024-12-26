package com.adamjulie.todo.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adamjulie.todo.list.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TaskListViewModel : ViewModel() {
    private val webService = Api.TaskWebService

    public val tasksStateFlow = MutableStateFlow<List<Task>>(emptyList())

    fun refresh() {
        viewModelScope.launch {
            val response = webService.fetchTasks() // Call HTTP (opération longue)
            if (!response.isSuccessful) { // à cette ligne, on a reçu la réponse de l'API
                Log.e("Network", "Error: ${response.message()}")
                return@launch
            }
            val fetchedTasks = response.body()!!
            tasksStateFlow.value = fetchedTasks // on modifie le flow, ce qui déclenche ses observers
        }
    }

    fun update(task: Task) {
        viewModelScope.launch {
            val response = webService.update(task = task, id = task.id)
            if (!response.isSuccessful) {
                Log.e("Network", "Error: ${response.raw()}")
                return@launch
            }

            val updatedTask = response.body()!!
            val updatedList = tasksStateFlow.value.map {
                if (it.id == updatedTask.id) updatedTask else it
            }
            tasksStateFlow.value = updatedList
        }
    }



    fun create(task: Task) {
        viewModelScope.launch {
            val response = webService.create(task = task)
            if (!response.isSuccessful) {
                Log.e("Network", "Error: ${response.raw()}")
                return@launch
            }

            val createdTask = response.body()!!
            val createdList = tasksStateFlow.value.map {
                if (it.id == createdTask.id) createdTask else it
            }
            tasksStateFlow.value = createdList
        }
    }



    fun delete(task: Task) {
        viewModelScope.launch {
            val response = webService.delete(id = task.id)
            if (!response.isSuccessful) {
                Log.e("Network", "Error: ${response.raw()}")
                return@launch
            }
        }
    }

    // à compléter plus tard:
    fun add(task: Task) {}
    fun edit(task: Task) {}
    fun remove(task: Task) {}
}