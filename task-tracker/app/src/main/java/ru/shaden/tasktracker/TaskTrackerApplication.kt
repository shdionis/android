package ru.shaden.tasktracker

import android.app.Application
import androidx.room.Room
import ru.shaden.tasktracker.model.room.TaskTrackerDatabase

class TaskTrackerApplication : Application() {
    lateinit var database: TaskTrackerDatabase

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(
            this,
            TaskTrackerDatabase::class.java,
            "d"
        ).createFromAsset("dump_dev_task_tracker.db").build()
    }

    companion object {
        lateinit var instance: TaskTrackerApplication
    }
}