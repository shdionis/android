package ru.shaden.tasktracker.model.room.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import ru.shaden.tasktracker.model.room.entities.ProjectEntity
import ru.shaden.tasktracker.model.room.entities.WorkspaceEntity

class ProjectWithWorkspaces {
    @Embedded
    lateinit var project: ProjectEntity;

    @Relation(
        parentColumn = "id",
        entityColumn = "project_id"
    )
    lateinit var workspaces: List<WorkspaceEntity>
}