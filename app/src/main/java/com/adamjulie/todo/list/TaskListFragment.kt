package com.adamjulie.todo.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.adamjulie.todo.R
import com.adamjulie.todo.TaskListAdapter

class TaskListFragment : Fragment() {

    private var taskList = listOf("Task 1", "Task 2", "Task 3")
    private val adapter = TaskListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_blank, container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_view)
        adapter.currentList = taskList
        recyclerView?.adapter = adapter
        return rootView
    }
}