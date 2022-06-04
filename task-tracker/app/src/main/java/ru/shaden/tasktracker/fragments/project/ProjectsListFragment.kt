package ru.shaden.tasktracker.fragments.project

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
import ru.shaden.tasktracker.model.Project
import ru.shaden.tasktracker.viewmodels.ProjectsListViewModel
import ru.shaden.tasktracker.viewmodels.State

class ProjectsListFragment : BaseFragment(), ProjectsRecyclerAdapter.Listener {
    private lateinit var recyclerView: RecyclerView
    private val viewModel: ProjectsListViewModel by activityViewModels {
        DumbDI.viewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.projects_list_fragment, container, false)
        val projectList = viewModel.getAllProjects()
        val adapter = ProjectsRecyclerAdapter(
            listener = this
        )
        recyclerView = view.findViewById(R.id.recycler_view_projects)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        projectList.observe(this.viewLifecycleOwner) {
            adapter.projects = it
            adapter.notifyDataSetChanged()
        }
        return view
    }

    override fun onClick(project: Project) {
        switch(State.WorkspacesListState(project))
    }
}


