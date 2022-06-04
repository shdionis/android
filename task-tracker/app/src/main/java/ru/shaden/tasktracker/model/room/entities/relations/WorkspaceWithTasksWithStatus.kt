package ru.shaden.tasktracker.model.room.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import ru.shaden.tasktracker.model.room.entities.TaskEntity
import ru.shaden.tasktracker.model.room.entities.WorkspaceEntity

class WorkspaceWithTasksWithStatus {
    @Embedded
    lateinit var workspace: WorkspaceEntity

    @Relation(
        entity = TaskEntity::class,
        parentColumn = "id",
        entityColumn = "workspace_id"
    )
    lateinit var tasks: List<TaskWithStatus>
}