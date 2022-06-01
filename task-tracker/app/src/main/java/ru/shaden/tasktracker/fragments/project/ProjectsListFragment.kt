package ru.shaden.tasktracker.fragments.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.shaden.tasktracker.R
import ru.shaden.tasktracker.fragments.BaseFragment
import ru.shaden.tasktracker.model.DumbModelUtils
import ru.shaden.tasktracker.model.Project
import ru.shaden.tasktracker.viewmodels.State

class ProjectsListFragment : BaseFragment(), ProjectsRecyclerAdapter.Listener {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.projects_list_fragment, container, false)
        recyclerView = view.findViewById(R.id.recycler_view_projects)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 5)
        recyclerView.adapter = ProjectsRecyclerAdapter(
            DumbModelUtils.projects.values.toList(),
            this
        )
        return view
    }

    override fun onClick(project: Project) {
        switch(State.WorkspacesListState(project))
    }

}


