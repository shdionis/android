package ru.shaden.tasktracker.di.component

import dagger.Component
import ru.shaden.tasktracker.TaskTrackerApplication
import ru.shaden.tasktracker.di.AndroidApplicationModule
import ru.shaden.tasktracker.di.ActivityTaskTrackerModule
import ru.shaden.tasktracker.di.TaskTrackerModule
import ru.shaden.tasktracker.di.scope.TaskTrackerScope

@TaskTrackerScope
@Component(
    modules = [
        AndroidApplicationModule::class,
        TaskTrackerModule::class
    ]
)
interface ApplicationComponent {

    fun initTaskActivityComponent(taskActivityTrackerModule: ActivityTaskTrackerModule): TaskListActivityComponent

    fun inject(taskTrackerApplication: TaskTrackerApplication)
}