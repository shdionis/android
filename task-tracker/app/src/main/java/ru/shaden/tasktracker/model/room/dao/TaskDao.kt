package ru.shaden.tasktracker.model.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.shaden.tasktracker.model.room.entities.TaskEntity
import ru.shaden.tasktracker.model.room.entities.relations.TaskWithStatus
import ru.shaden.tasktracker.model.room.entities.relations.TaskWithStatusWithWorkspace

@Dao
interface TaskDao {
    @Query("Select * from tasks")
    fun getTaskWithStatus(): TaskWithStatus

    @Query("Select * from tasks where workspace_id = :workspaceId")
    fun getTaskWithStatusListByWorkspaceId(workspaceId: Int): Flow<List<TaskWithStatus>>

    @Query("Select * from tasks where id = :taskId and workspace_id = :workspaceId")
    fun getTaskWithStatusWithWorkspace(
        taskId: Int,
        workspaceId: Int
    ): Flow<TaskWithStatusWithWorkspace>

    @Insert
    fun addTask(project: TaskEntity)

    @Delete
    fun deleteTask(project: TaskEntity)

    @Update
    fun updateTask(project: TaskEntity)
}