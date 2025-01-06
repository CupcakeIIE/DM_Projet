package com.adamjulie.todo.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.adamjulie.todo.R
import com.adamjulie.todo.TaskListAdapter
import com.adamjulie.todo.TaskListListener
import com.adamjulie.todo.data.Api
import com.adamjulie.todo.data.TaskListViewModel
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
        Task(id = "id_2", title = "Task 2", description = " "),
        Task(id = "id_3", title = "Task 3", description = " ")
    )
    //private val adapter = TaskListAdapter()

    private lateinit var userTextView: TextView
    private lateinit var userImageView: ImageView
    private val viewModel: TaskListViewModel by viewModels()


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

        // Initialiser la TextView
        userTextView = rootView.findViewById(R.id.userTextView)

        userImageView = rootView.findViewById(R.id.imageView)


        lifecycleScope.launch { // on lance une coroutine car `collect` est `suspend`
            viewModel.tasksStateFlow.collect { newList ->
                // cette lambda est exécutée à chaque fois que la liste est mise à jour dans le VM
                // -> ici, on met à jour la liste dans l'adapter
                taskList = newList;
                refreshAdapter();
            }
        }

        return rootView
    }

    private fun refreshAdapter(){
        adapter.submitList(taskList)
        adapter.notifyDataSetChanged()
    }


    override fun onResume() {
        super.onResume()
        // Lancer une coroutine dans le contexte de lifecycleScope
        lifecycleScope.launch {
            val user = Api.userWebService.fetchUser().body()!!
            userTextView.text = user.name
        }

        userImageView.load("https://goo.gl/gEgYUd")

       // viewModel.refresh() // on demande de rafraîchir les données sans attendre le retour directement
    }


}