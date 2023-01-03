package ru.shaden.tasktracker.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.shaden.tasktracker.di.scope.TaskTrackerScope
import ru.shaden.tasktracker.model.room.TaskTrackerDatabase

@Module
class AndroidApplicationModule(
    private val context: Context,
) {
    @TaskTrackerScope
    @Provides
    fun provideTaskTrackerDatabase(): TaskTrackerDatabase {
        return Room.databaseBuilder(
            context,
            TaskTrackerDatabase::class.java,
            "d"
        ).createFromAsset("dump_dev_task_tracker.db").build()
    }

    @Provides
    fun provideApplicationContext(): Context = context
}