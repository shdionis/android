package ru.shaden.tasktracker.di

import dagger.Module
import dagger.Provides
import ru.shaden.tasktracker.di.scope.TaskTrackerScope
import ru.shaden.tasktracker.model.room.TaskTrackerDatabase
import ru.shaden.tasktracker.model.room.repositories.ProjectRepository
import ru.shaden.tasktracker.model.room.repositories.TaskRepository
import ru.shaden.tasktracker.model.room.repositories.WorkspaceRepository

@Module
class TaskTrackerModule {
    @TaskTrackerScope
    @Provides
    fun provideProjectRepository(taskTrackerDatabase: TaskTrackerDatabase): ProjectRepository {
        return ProjectRepository(taskTrackerDatabase.projectDao())
    }

    @TaskTrackerScope
    @Provides
    fun provideWorkspaceRepository(taskTrackerDatabase: TaskTrackerDatabase): WorkspaceRepository {
        return WorkspaceRepository(taskTrackerDatabase.workspaceDao())
    }

    @TaskTrackerScope
    @Provides
    fun provideTaskRepository(taskTrackerDatabase: TaskTrackerDatabase): TaskRepository {
        return TaskRepository(taskTrackerDatabase.taskDao())
    }
}