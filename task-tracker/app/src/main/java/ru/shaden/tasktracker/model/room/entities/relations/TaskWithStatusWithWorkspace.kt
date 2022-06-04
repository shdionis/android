package ru.shaden.tasktracker.model.room.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import ru.shaden.tasktracker.model.Workspace
import ru.shaden.tasktracker.model.room.entities.StatusEntity
import ru.shaden.tasktracker.model.room.entities.TaskEntity
import ru.shaden.tasktracker.model.room.entities.WorkspaceEntity

class TaskWithStatusWithWorkspace {
    @Embedded
    lateinit var task: TaskEntity

    @Relation(
        parentColumn = "workspace_id",
        entityColumn = "id"
    )
    lateinit var workspace: WorkspaceEntity

    @Relation(
        parentColumn = "status_id",
        entityColumn = "id"
    )
    lateinit var status: StatusEntity
}