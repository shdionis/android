package ru.shaden.tasktracker.fragments.project

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.shaden.tasktracker.R
import ru.shaden.tasktracker.model.Project

class ProjectsRecyclerViewHolder(
    private val view: View,
    private val listener: ProjectsRecyclerAdapter.Listener,
) : RecyclerView.ViewHolder(view) {
    private val projectTitle: TextView = view.findViewById(R.id.project_title)
    private val projectDescription: TextView = view.findViewById(R.id.project_description)

    fun bind(project: Project) {
        projectTitle.text = project.name
        projectDescription.text = project.description
        view.setOnClickListener {
            listener.onClick(project)
        }
    }
}