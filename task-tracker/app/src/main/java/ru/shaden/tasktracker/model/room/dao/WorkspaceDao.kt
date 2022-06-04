package ru.shaden.tasktracker.model.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.shaden.tasktracker.model.room.entities.WorkspaceEntity
import ru.shaden.tasktracker.model.room.entities.relations.WorkspaceWithTasksList
import ru.shaden.tasktracker.model.room.entities.relations.WorkspaceWithTasksWithStatus

@Dao
interface WorkspaceDao {
    @Query("Select * From workspaces where project_id = :projectId")
    fun getWorkspacesByProjectId(projectId : Int): Flow<List<WorkspaceEntity>>


    @Query("Select * From workspaces where id = :workspaceId")
    fun getWorkspaceWithTaskWithStatusId(workspaceId : Int): Flow<WorkspaceWithTasksWithStatus>

    @Insert
    fun addWorkspace(workspace: WorkspaceEntity)

    @Delete
    fun deleteWorkspace(workspace: WorkspaceEntity)

    @Update
    fun updateWorkspace(workspace: WorkspaceEntity)
}