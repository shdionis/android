package ru.shaden.tasktracker.di

import dagger.Module
import dagger.Provides
import ru.shaden.tasktracker.di.scope.ActivityScope
import ru.shaden.tasktracker.fragments.TaskTrackerFragmentFactory
import ru.shaden.tasktracker.model.room.repositories.ProjectRepository
import ru.shaden.tasktracker.model.room.repositories.TaskRepository
import ru.shaden.tasktracker.model.room.repositories.WorkspaceRepository
import ru.shaden.tasktracker.viewmodels.TaskTrackerViewModelProviderFactory

@Module
class ActivityTaskTrackerModule {
    @ActivityScope
    @Provides
    fun provideViewModelProviderFactory(
        projectRepository: ProjectRepository,
        workspaceRepository: WorkspaceRepository,
        taskRepository: TaskRepository,
    ): TaskTrackerViewModelProviderFactory {
        return TaskTrackerViewModelProviderFactory(
            projectRepository,
            workspaceRepository,
            taskRepository
        )
    }

    @ActivityScope
    @Provides
    fun provideTaskTrackerFragmentFactory(
        taskTrackerViewModelProviderFactory: TaskTrackerViewModelProviderFactory
    ): TaskTrackerFragmentFactory {
        return TaskTrackerFragmentFactory(taskTrackerViewModelProviderFactory)
    }
}