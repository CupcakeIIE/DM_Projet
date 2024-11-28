package com.adamjulie.todo.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.adamjulie.todo.R

class TaskListFragment : Fragment() {

    private var taskList = listOf("Task 1", "Task 2", "Task 3")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_blank, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}