package ru.shaden.tasktracker.di

import ru.shaden.tasktracker.TaskTrackerApplication
import ru.shaden.tasktracker.fragments.TaskTrackerFragmentFactory
import ru.shaden.tasktracker.model.room.TaskTrackerDatabase
import ru.shaden.tasktracker.model.room.repositories.ProjectRepository
import ru.shaden.tasktracker.model.room.repositories.TaskRepository
import ru.shaden.tasktracker.model.room.repositories.WorkspaceRepository
import ru.shaden.tasktracker.viewmodels.TaskTrackerViewModelProviderFactory

object DumbDI {
    val application: TaskTrackerApplication by lazy { TaskTrackerApplication.instance }
    val database: TaskTrackerDatabase by lazy { application.database }
    val projectRepository: ProjectRepository by lazy { ProjectRepository(database.projectDao()) }
    val workspaceRepository: WorkspaceRepository by lazy { WorkspaceRepository(database.workspaceDao()) }
    val taskRepository: TaskRepository by lazy { TaskRepository(database.taskDao()) }
    val viewModelProviderFactory: TaskTrackerViewModelProviderFactory by lazy {
        TaskTrackerViewModelProviderFactory(
            projectRepository,
            workspaceRepository,
            taskRepository
        )
    }
    val taskTrackerFragmentFactory: TaskTrackerFragmentFactory by lazy { TaskTrackerFragmentFactory() }
}