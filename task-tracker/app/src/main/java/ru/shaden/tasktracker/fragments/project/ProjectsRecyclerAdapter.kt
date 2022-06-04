package ru.shaden.tasktracker.fragments.project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.shaden.tasktracker.R
import ru.shaden.tasktracker.model.Project

class ProjectsRecyclerAdapter(
    internal var projects: List<Project> = ArrayList(),
    private val listener: Listener,
) : RecyclerView.Adapter<ProjectsRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.projects_list_item, parent, false)
        return ProjectsRecyclerViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ProjectsRecyclerViewHolder, position: Int) {
        val project = projects[position]
        holder.bind(project)
    }

    override fun getItemCount(): Int = projects.size ?: 0

    interface Listener {
        fun onClick(project: Project)
    }
}
