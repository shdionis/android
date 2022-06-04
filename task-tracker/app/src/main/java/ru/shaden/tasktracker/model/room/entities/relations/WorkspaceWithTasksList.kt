package ru.shaden.tasktracker.model.room.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import ru.shaden.tasktracker.model.room.entities.WorkspaceEntity

class WorkspaceWithTasksList {
    @Embedded
    lateinit var workspace: WorkspaceEntity

    @Relation(
        parentColumn = "id",
        entityColumn = "workspace_id"
    )
    lateinit var tasksList: List<TaskWithStatus>
}