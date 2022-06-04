package ru.shaden.tasktracker.model.room.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.shaden.tasktracker.model.Task
import ru.shaden.tasktracker.model.room.dao.TaskDao
import ru.shaden.tasktracker.model.room.repositories.mappers.EntitiesToUiMapper

class TaskRepository(
    private val taskDao: TaskDao,
) {
    fun getTaskWithStatusWithWorkspace(taskId: Int, workspaceId: Int): Flow<Task> =
        taskDao.getTaskWithStatusWithWorkspace(taskId, workspaceId).map {
            EntitiesToUiMapper.mapTaskWithStatusWithWorkspace(it)
        }
}