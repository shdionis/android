package ru.shaden.tasktracker.fragments.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.shaden.tasktracker.R
import ru.shaden.tasktracker.fragments.BaseFragment
import ru.shaden.tasktracker.model.DumbModelUtils
import ru.shaden.tasktracker.model.Task
import ru.shaden.tasktracker.viewmodels.State

class TasksListFragment : BaseFragment(), TasksListRecyclerAdapter.Listener {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.tasks_list_fragment, container, false)
        val workspaceId = arguments?.getInt(ARG_WORKSPACE_ID) ?: return view
        recyclerView = view.findViewById(R.id.recycler_view_tasks)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = DumbModelUtils.tasks[workspaceId]?.values?.toList()
            ?.let { TasksListRecyclerAdapter(this, it) }
        return view
    }

    override fun onClick(task: Task) {
        switch(State.TaskDetailsState(task))
    }

    companion object {
        const val ARG_WORKSPACE_ID = "ARG_WORKSPACE_ID"
    }
}