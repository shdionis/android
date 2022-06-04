package ru.shaden.tasktracker.model.room.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.shaden.tasktracker.model.Workspace
import ru.shaden.tasktracker.model.room.dao.WorkspaceDao
import ru.shaden.tasktracker.model.room.repositories.mappers.EntitiesToUiMapper

class WorkspaceRepository(
    private val workspaceDao: WorkspaceDao,
) {
    fun getWorkspacesByProjectId(projectId: Int): Flow<List<Workspace>> =
        workspaceDao.getWorkspacesByProjectId(projectId).map {
            EntitiesToUiMapper.mapWorkspacesList(it)
        }

    fun getWorkspaceWithTasksWithStatus(workspaceId: Int): Flow<Workspace> =
        workspaceDao.getWorkspaceWithTaskWithStatusId(workspaceId).map {
            EntitiesToUiMapper.mapWorkspaceWithTasksWithStatus(it)
        }
}