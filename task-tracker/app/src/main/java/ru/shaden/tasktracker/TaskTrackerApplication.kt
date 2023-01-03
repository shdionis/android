package ru.shaden.tasktracker

import android.app.Application
import ru.shaden.tasktracker.di.AndroidApplicationModule
import ru.shaden.tasktracker.di.component.ApplicationComponent
import ru.shaden.tasktracker.di.component.DaggerApplicationComponent

class TaskTrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
            .androidApplicationModule(AndroidApplicationModule(this))
            .build()
    }

    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }
}