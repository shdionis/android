package ru.shaden.tasktracker.di.component

import dagger.Subcomponent
import ru.shaden.tasktracker.ContainerActivity
import ru.shaden.tasktracker.di.ActivityTaskTrackerModule
import ru.shaden.tasktracker.di.scope.ActivityScope

@ActivityScope
@Subcomponent(modules = [ActivityTaskTrackerModule::class])
interface TaskListActivityComponent {
    fun inject(containerActivity: ContainerActivity)
}