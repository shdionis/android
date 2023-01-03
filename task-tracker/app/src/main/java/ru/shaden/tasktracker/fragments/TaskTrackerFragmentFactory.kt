package ru.shaden.tasktracker.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import ru.shaden.tasktracker.fragments.project.ProjectsListFragment
import ru.shaden.tasktracker.fragments.task.TaskDetailsFragment
import ru.shaden.tasktracker.fragments.task.TasksListFragment
import ru.shaden.tasktracker.fragments.workspace.WorkspacesListFragment
import ru.shaden.tasktracker.viewmodels.TaskTrackerViewModelProviderFactory

class TaskTrackerFragmentFactory(
    private val viewModelProviderFactory: TaskTrackerViewModelProviderFactory
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ProjectsListFragment::class.java.name -> createProjectsListFragment()
            WorkspacesListFragment::class.java.name -> createWorkspacesListFragment()
            TasksListFragment::class.java.name -> createTasksListFragment()
            TaskDetailsFragment::class.java.name -> createTasksDetailsFragment()
            else -> super.instantiate(classLoader, className)
        }
    }

    fun createProjectsListFragment(): Fragment =
        ProjectsListFragment(viewModelProviderFactory)

    fun createWorkspacesListFragment(): Fragment =
        WorkspacesListFragment(viewModelProviderFactory)

    fun createTasksListFragment(): Fragment =
        TasksListFragment(viewModelProviderFactory)

    fun createTasksDetailsFragment(): Fragment =
        TaskDetailsFragment(viewModelProviderFactory)
}