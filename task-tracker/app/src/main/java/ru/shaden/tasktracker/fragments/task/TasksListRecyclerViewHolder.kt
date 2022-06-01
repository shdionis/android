package ru.shaden.tasktracker.fragments.task

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.shaden.tasktracker.R
import ru.shaden.tasktracker.model.Task

class TasksListRecyclerViewHolder(
    private val view: View,
    private val listener: TasksListRecyclerAdapter.Listener
) : RecyclerView.ViewHolder(view) {
    private val taskTitle: TextView = view.findViewById(R.id.task_title)
    private val taskStatus: TextView = view.findViewById(R.id.task_status)

    fun bind(task: Task) {
        taskTitle.text = view.context.resources.getString(
            R.string.task_name_format,
            task.workspace.name,
            task.name
        )
        taskStatus.text = task.status.name
        view.setOnClickListener {
            listener.onClick(task)
        }
    }
}