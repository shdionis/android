package ru.shaden.tasktracker.model.room.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.shaden.tasktracker.model.Project
import ru.shaden.tasktracker.model.room.dao.ProjectDao
import ru.shaden.tasktracker.model.room.repositories.mappers.EntitiesToUiMapper

class ProjectRepository(
    private val projectDao: ProjectDao,
) {
    fun getAllProjects(): Flow<List<Project>> {
        return projectDao.getAllProjects().map { EntitiesToUiMapper.mapProjectsList(it) }
    }
}