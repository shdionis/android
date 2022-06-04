package ru.shaden.tasktracker.model.room.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import ru.shaden.tasktracker.model.room.entities.StatusEntity
import ru.shaden.tasktracker.model.room.entities.TaskEntity

class TaskWithStatus {

    @Embedded
    lateinit var task: TaskEntity

    @Relation(
        parentColumn = "status_id",
        entityColumn = "id"
    )
    lateinit var status: StatusEntity
}