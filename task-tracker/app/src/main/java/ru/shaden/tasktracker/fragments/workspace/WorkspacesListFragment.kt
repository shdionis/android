package ru.shaden.tasktracker.fragments.workspace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.shaden.tasktracker.R
import ru.shaden.tasktracker.fragments.BaseFragment
import ru.shaden.tasktracker.model.DumbModelUtils
import ru.shaden.tasktracker.model.Workspace
import ru.shaden.tasktracker.viewmodels.State

class WorkspacesListFragment : BaseFragment(), WorkspacesRecyclerAdapter.Listener  {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.workspaces_list_fragment, container, false)
        val projectId = arguments?.getInt(ARG_PROJECT_ID) ?: return view
        val workspaces = DumbModelUtils.workspaces[projectId]?.values?.toList() ?: return view
        recyclerView = view.findViewById(R.id.recycler_view_workspaces)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = WorkspacesRecyclerAdapter(
            workspaces,
            this
        )
        return view
    }

    override fun onClick(workspace: Workspace) {
        switch(State.TasksListState(workspace))
    }

    companion object {
        const val ARG_PROJECT_ID = "ARG_PROJECT_ID"
    }
}