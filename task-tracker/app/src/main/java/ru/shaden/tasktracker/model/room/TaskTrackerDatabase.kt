package ru.shaden.tasktracker.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.shaden.tasktracker.model.room.dao.ProjectDao
import ru.shaden.tasktracker.model.room.dao.TaskDao
import ru.shaden.tasktracker.model.room.dao.WorkspaceDao
import ru.shaden.tasktracker.model.room.entities.ProjectEntity
import ru.shaden.tasktracker.model.room.entities.StatusEntity
import ru.shaden.tasktracker.model.room.entities.TaskEntity
import ru.shaden.tasktracker.model.room.entities.WorkspaceEntity

@Database(
    entities = [
        ProjectEntity::class,
        WorkspaceEntity::class,
        TaskEntity::class,
        StatusEntity::class
    ],
    version = 1
)
abstract class TaskTrackerDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
    abstract fun workspaceDao(): WorkspaceDao
    abstract fun taskDao(): TaskDao
}