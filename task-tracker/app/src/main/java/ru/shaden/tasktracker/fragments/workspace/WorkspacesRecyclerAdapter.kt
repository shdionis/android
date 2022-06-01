package ru.shaden.tasktracker.fragments.workspace

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.shaden.tasktracker.R
import ru.shaden.tasktracker.model.Workspace

class WorkspacesRecyclerAdapter(
    private val list: List<Workspace>,
    private val listener: Listener
) : RecyclerView.Adapter<WorkspacesRecyclerViewHolder>() {


    interface Listener {
        fun onClick(workspace: Workspace)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkspacesRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.workspace_list_item, parent, false)
        return WorkspacesRecyclerViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: WorkspacesRecyclerViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
}
