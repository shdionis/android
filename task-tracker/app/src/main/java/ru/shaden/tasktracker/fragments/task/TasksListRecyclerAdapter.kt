package ru.shaden.tasktracker.fragments.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.shaden.tasktracker.R
import ru.shaden.tasktracker.model.Task

class TasksListRecyclerAdapter(
    internal var items: List<Task> = ArrayList(),
    private val listener: Listener,
) : RecyclerView.Adapter<TasksListRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksListRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tasks_list_item, parent, false)
        return TasksListRecyclerViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: TasksListRecyclerViewHolder, position: Int) {
        val task = items[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = items.size

    interface Listener {
        fun onClick(task: Task)
    }
}