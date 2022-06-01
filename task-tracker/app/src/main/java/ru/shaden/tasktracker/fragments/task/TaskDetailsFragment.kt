package ru.shaden.tasktracker.fragments.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import ru.shaden.tasktracker.R
import ru.shaden.tasktracker.fragments.BaseFragment
import ru.shaden.tasktracker.model.DumbModelUtils
import ru.shaden.tasktracker.model.Task

class TaskDetailsFragment : BaseFragment() {

    private lateinit var taskTitle: EditText
    private lateinit var taskStatus: TextView
    private lateinit var taskDescription: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.task_details_fragment, container, false)
        val task = getTask() ?: return view
        taskTitle = view.findViewById(R.id.task_title_edit_text)
        taskStatus = view.findViewById(R.id.task_status)
        taskDescription = view.findViewById(R.id.task_description)

        fillData(task)
        return view
    }

    private fun fillData(task: Task) {
        context?.getString(R.string.task_name_format, task.workspace.name, task.name)?.let {
            taskTitle.setText(it)
        } ?: return

        taskStatus.text = task.status.name
        taskDescription.text = task.description
    }

    private fun getTask(): Task? {
        val taskId = arguments?.getInt(ARG_TASK_ID) ?: return null
        val workspaceId = arguments?.getInt(ARG_WORKSPACE_ID) ?: return null
        return DumbModelUtils.tasks[workspaceId]?.let { it[taskId] }
    }

    companion object {
        const val ARG_TASK_ID = "ARG_TASK_ID"
        const val ARG_WORKSPACE_ID = "ARG_WORKSPACE_ID"
    }
}