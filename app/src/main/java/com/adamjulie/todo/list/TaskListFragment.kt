package com.adamjulie.todo.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.adamjulie.todo.R
import com.adamjulie.todo.TaskListAdapter
import com.adamjulie.todo.TaskListListener
import com.adamjulie.todo.data.Api
import com.adamjulie.todo.detail.DetailActivity
import kotlinx.coroutines.launch

class TaskListFragment : Fragment() {

    val adapterListener : TaskListListener = object : TaskListListener {
        override fun onClickDelete(task: Task) = Unit
        override fun onClickEdit(task: Task) = Unit
    }
    val adapter = TaskListAdapter(adapterListener)

    private var taskList = listOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )
    //private val adapter = TaskListAdapter()


    companion object {
        const val TASK_KEY = "task"
    }

    val editTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        val task = result.data?.getSerializableExtra(TASK_KEY) as Task?
        if (task != null) {
            taskList = taskList.map { if (it.id == task.id) task else it }
            refreshAdapter()
        }

    }

    val createTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        val task = result.data?.getSerializableExtra(TASK_KEY) as Task?
        if (task != null) {
            taskList = taskList + task
            refreshAdapter()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_blank, container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_view)
        val intent = Intent(context, DetailActivity::class.java)
        recyclerView?.adapter = adapter
        adapter.submitList(taskList)
        val button_edit = rootView.findViewById<View>(R.id.button_edit)
        button_edit.setOnClickListener {
            if(taskList.isNotEmpty())
                adapter.onClickEdit(taskList.last())
        }
        val button_add = rootView.findViewById<View>(R.id.button_add)
        button_add.setOnClickListener {
            createTask.launch(intent)
        }
        //"implémentation" de la lambda dans le fragment, pour que la lambda aie un effet:
        adapter.onClickDelete = {
            taskList = taskList.filter { task -> task.id != it.id }
            refreshAdapter()
        }

        adapter.onClickEdit = {
            editTask.launch(intent.putExtra(TASK_KEY, it))
        }

        val buttonDelete = rootView.findViewById<View>(R.id.button_delete)
        buttonDelete.setOnClickListener {
            if(taskList.isNotEmpty())
                adapter.onClickDelete(taskList.last())
        }


        return rootView
    }

    private fun refreshAdapter(){
        adapter.submitList(taskList)
        adapter.notifyDataSetChanged()
    }



}