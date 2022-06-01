package ru.shaden.tasktracker.fragments.workspace

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.shaden.tasktracker.R
import ru.shaden.tasktracker.model.Workspace

class WorkspacesRecyclerViewHolder(
    private val view: View,
    private val listener: WorkspacesRecyclerAdapter.Listener,
) : RecyclerView.ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.workspace_title)
    private val description: TextView = view.findViewById(R.id.workspace_description)

    fun bind(item: Workspace) {
        title.text = item.name
        description.text = item.description
        view.setOnClickListener {
            listener.onClick(item)
        }
    }

}
