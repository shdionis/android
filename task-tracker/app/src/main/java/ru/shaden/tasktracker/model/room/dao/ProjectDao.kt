package ru.shaden.tasktracker.model.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.shaden.tasktracker.model.room.entities.ProjectEntity
import ru.shaden.tasktracker.model.room.entities.relations.ProjectWithWorkspaces

@Dao
interface ProjectDao {
    @Query("Select * From projects")
    fun getAllProjects(): Flow<List<ProjectEntity>>

    @Query("Select * From projects")
    fun getProjectWithWorkspace(): Flow<List<ProjectWithWorkspaces>>

    @Insert
    fun addProject(project: ProjectEntity)

    @Delete
    fun deleteProject(project: ProjectEntity)

    @Update
    fun updateProject(project: ProjectEntity)
}