package com.adamjulie.todo.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.adamjulie.todo.R
import com.adamjulie.todo.TaskListAdapter
import java.util.UUID

class TaskListFragment : Fragment() {

    private var taskList = listOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )
    private val adapter = TaskListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_blank, container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView?.adapter = adapter
        adapter.submitList(taskList)
        val button_add = rootView.findViewById<View>(R.id.button_add)
        button_add.setOnClickListener {
            taskList = taskList + Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
            refreshAdapter()
        }
        return rootView
    }

    private fun refreshAdapter(){
        adapter.submitList(taskList)
        adapter.notifyDataSetChanged()
    }

}