package ru.shaden.tasktracker.model.room.repositories.mappers

import ru.shaden.tasktracker.model.Project
import ru.shaden.tasktracker.model.Status
import ru.shaden.tasktracker.model.Task
import ru.shaden.tasktracker.model.Workspace
import ru.shaden.tasktracker.model.room.entities.ProjectEntity
import ru.shaden.tasktracker.model.room.entities.StatusEntity
import ru.shaden.tasktracker.model.room.entities.TaskEntity
import ru.shaden.tasktracker.model.room.entities.WorkspaceEntity
import ru.shaden.tasktracker.model.room.entities.relations.ProjectWithWorkspaces
import ru.shaden.tasktracker.model.room.entities.relations.TaskWithStatus
import ru.shaden.tasktracker.model.room.entities.relations.TaskWithStatusWithWorkspace
import ru.shaden.tasktracker.model.room.entities.relations.WorkspaceWithTasksWithStatus

object EntitiesToUiMapper {

    fun mapProjectWithWorkspaces(projects: List<ProjectWithWorkspaces>): List<Project> {
        return projects.map { projectWithWorkspace ->
            mapProject(projectWithWorkspace.project).also { project ->
                project.workspacesList =
                    projectWithWorkspace.workspaces.map { mapWorkspace(it) }
            }
        }
    }

    fun mapProjectsList(projects: List<ProjectEntity>): List<Project> =
        projects.map { mapProject(it) }


    fun mapWorkspacesList(workspaces: List<WorkspaceEntity>): List<Workspace> =
        workspaces.map { mapWorkspace(it) }

    fun mapTaskWithStatusList(tasksWithStatus: List<TaskWithStatus>): List<Task> =
        tasksWithStatus.map { mapTaskWithStatus(it) }

    fun mapTaskWithStatusWithWorkspace(taskWithStatusWithWorkspace: TaskWithStatusWithWorkspace): Task =
        mapTask(taskWithStatusWithWorkspace.task).also {
            it.workspace = mapWorkspace(taskWithStatusWithWorkspace.workspace)
            it.status = mapStatus(taskWithStatusWithWorkspace.status)
        }

    fun mapWorkspaceWithTasksWithStatus(workspaceTasksStatus: WorkspaceWithTasksWithStatus): Workspace =
        mapWorkspace(workspaceTasksStatus.workspace).also { wp ->
            wp.tasks = workspaceTasksStatus.tasks.map { task ->
                mapTaskWithStatus(task).also {
                    it.workspace = wp
                }
            }
        }

    private fun mapTaskWithStatus(taskWithStatus: TaskWithStatus): Task =
        mapTask(taskWithStatus.task).also {
            it.status = mapStatus(taskWithStatus.status)
        }

    private fun mapProject(dbEntity: ProjectEntity): Project =
        UiMapperProject(
            id = dbEntity.projectId,
            name = dbEntity.name,
            description = dbEntity.description
        )

    private fun mapWorkspace(dbEntity: WorkspaceEntity): Workspace =
        UiMapperWorkspace(
            id = dbEntity.workspaceId,
            name = dbEntity.name,
            description = dbEntity.description
        )

    private fun mapTask(dbEntity: TaskEntity): Task =
        UiMapperTask(
            id = dbEntity.taskId,
            name = dbEntity.name,
            description = dbEntity.description
        )

    private fun mapStatus(dbEntity: StatusEntity): Status =
        UiMapperStatus(
            id = dbEntity.statusId,
            name = dbEntity.name
        )


    private data class UiMapperProject(
        override val id: Int,
        override var name: String,
        override var description: String?,
        override var workspacesList: List<Workspace>? = null,
    ) : Project

    private data class UiMapperWorkspace(
        override val id: Int,
        override var name: String,
        override var description: String?,
        override var project: Project? = null,
        override var tasks: List<Task>? = ArrayList(),
    ) : Workspace

    private data class UiMapperStatus(
        override val id: Int,
        override val name: String,
    ) : Status

    private data class UiMapperTask(
        override val id: Int,
        override var name: String,
        override var description: String?,
        override var workspace: Workspace? = null,
        override var status: Status? = null,
    ) : Task
}