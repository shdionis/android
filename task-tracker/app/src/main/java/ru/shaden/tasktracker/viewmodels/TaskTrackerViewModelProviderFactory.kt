package ru.shaden.tasktracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.shaden.tasktracker.model.room.repositories.ProjectRepository
import ru.shaden.tasktracker.model.room.repositories.TaskRepository
import ru.shaden.tasktracker.model.room.repositories.WorkspaceRepository

class TaskTrackerViewModelProviderFactory(
    private val projectRepository: ProjectRepository,
    private val workspaceRepository: WorkspaceRepository,
    private val taskRepository: TaskRepository,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            ProjectsListViewModel::class.java -> createProjectListViewModel() as T
            WorkspacesListViewModel::class.java -> createWorkspacesListViewModel() as T
            TasksListViewModel::class.java -> createTasksListViewModel() as T
            TaskDetailsViewModel::class.java -> createTaskDetailsViewModel() as T
            else -> super.create(modelClass)
        }
    }

    private fun createProjectListViewModel(): ProjectsListViewModel {
        return ProjectsListViewModel(projectRepository)
    }

    private fun createWorkspacesListViewModel(): WorkspacesListViewModel {
        return WorkspacesListViewModel(workspaceRepository)
    }

    private fun createTasksListViewModel(): TasksListViewModel {
        return TasksListViewModel(taskRepository, workspaceRepository)
    }

    private fun createTaskDetailsViewModel(): TaskDetailsViewModel {
        return TaskDetailsViewModel(taskRepository, workspaceRepository)
    }
}