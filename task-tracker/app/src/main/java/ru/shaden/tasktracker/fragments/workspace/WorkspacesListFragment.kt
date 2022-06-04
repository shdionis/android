package ru.shaden.tasktracker.fragments.workspace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.shaden.tasktracker.R
import ru.shaden.tasktracker.di.DumbDI
import ru.shaden.tasktracker.fragments.BaseFragment
import ru.shaden.tasktracker.model.Workspace
import ru.shaden.tasktracker.viewmodels.State
import ru.shaden.tasktracker.viewmodels.WorkspacesListViewModel

class WorkspacesListFragment : BaseFragment(), WorkspacesRecyclerAdapter.Listener {
    private lateinit var recyclerView: RecyclerView
    private val viewModel: WorkspacesListViewModel by activityViewModels {
        DumbDI.viewModelProviderFactory
    }

    lateinit var adapter: WorkspacesRecyclerAdapter;
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.workspaces_list_fragment, container, false)
        val projectId = arguments?.getInt(ARG_PROJECT_ID) ?: return view
        adapter = WorkspacesRecyclerAdapter(
            listener = this
        )
        recyclerView = view.findViewById(R.id.recycler_view_workspaces)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        viewModel.getWorkspacesByProject(projectId).observe(viewLifecycleOwner) {
            adapter.items = it
            adapter.notifyDataSetChanged()
        }
        return view
    }

    override fun onClick(workspace: Workspace) {
        switch(State.TasksListState(workspace))
    }

    companion object {
        const val ARG_PROJECT_ID = "ARG_PROJECT_ID"
    }
}